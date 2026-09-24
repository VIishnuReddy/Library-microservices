package com.example.library.services;

import com.example.library.dtos.BookItemResponseDto;
import com.example.library.exceptions.BookNotFoundException;
import com.example.library.models.*;
import com.example.library.reposiories.BookItemRepository;
import com.example.library.reposiories.BookRepository;
import com.example.library.strategies.BookingStrategy;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class BookService {

    private final Map<TransactionType, BookingStrategy> strategyMap;
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;
    private TransactionService transactionService;
    private ReturnService returnService;
    private final StringRedisTemplate redisTemplate;

    public BookService(List<BookingStrategy> strategies,
                       BookRepository bookRepository,
                       BookItemRepository bookItemRepository,
                        ReturnService returnService,
                       TransactionService transactionService,
                       StringRedisTemplate redisTemplate) {

        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;

        this.strategyMap = new HashMap<>();
        for (BookingStrategy strategy : strategies) {
            strategyMap.put(strategy.getTransactionType(), strategy);
        }
        this.returnService=returnService;
        this.transactionService=transactionService;
        this.redisTemplate=redisTemplate;
    }

    public BookItem handleTransaction(TransactionType type, String userId, Long bookId) {
        BookingStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new RuntimeException("No strategy found for transaction type " + type);
        }
        BookItem bookItem = strategy.processTransaction(userId, bookId);
        transactionService.recordTransaction(bookItem.getBook().getName(),
                type.name(), bookItem.getUserId(), bookItem.getBorrowDate());
        return bookItem;
    }

    public String returnBook(String barcode) {
       Optional<BookItem> bookItem= bookItemRepository.findByBarcode(barcode);
        transactionService.recordTransaction(bookItem.get().getBook().getName(),
                                                TransactionType.RETURN.name(), bookItem.get().getUserId(),
                                                bookItem.get().getBorrowDate());
        return returnService.returnBook(barcode);
    }

    @Cacheable(value = "booksList")
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @CachePut(value = "books", key = "#result.id")
    public Book addBook(Book book, String idempotencyKey){

        String key = "idempotency:book: "+idempotencyKey;
        Boolean newRequest = redisTemplate.opsForValue().
                 setIfAbsent(key, "PROCESSING",
                         5, TimeUnit.MINUTES);

        if(Boolean.FALSE.equals(newRequest)){
            throw new RuntimeException("Duplicate Request, Book already added");
        }

        try{
            Book savedBook = bookRepository.save(book);
            redisTemplate.opsForValue().set(key, savedBook.getId().toString(),5,TimeUnit.MINUTES);
            return savedBook;
        }catch(Exception ex){
            redisTemplate.delete(key);
            throw ex;
        }
    }

    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()->
                new BookNotFoundException("Please enter a valid book id:"+id));
    }

    @CachePut(value = "books", key = "#id")
    public Book updateBook(Long id, Book book){
        Book existingbook = bookRepository.findById(id).orElseThrow(()
                -> new BookNotFoundException("Book not found with id "+id));

        existingbook.setName(book.getName());
        existingbook.setPrice(book.getPrice());
        existingbook.setGenre(book.getGenre());
        existingbook.setBookstatus(book.getBookstatus());
        existingbook.setQuantity(book.getQuantity());

        return bookRepository.save(existingbook);
    }

    public List<Book> findBooksByBookStatus(BookStatus bookStatus){
        return bookRepository.findBooksByBookstatus(bookStatus);
    }

    // checking for the book which are bought or rented
    public List<BookItem> getBooksByTransactionType(TransactionType transactionType){
        return bookItemRepository.findBookItemsByTransactionType(transactionType);
    }
}
