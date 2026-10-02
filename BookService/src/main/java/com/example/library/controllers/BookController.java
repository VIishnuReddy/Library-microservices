package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.models.BookStatus;
import com.example.library.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    BookService bookService;

    BookController(BookService bookService){
        this.bookService=bookService;
    }

    @GetMapping("/getBooks")
    public List<Book> getBooks(){
        log.info("fetching all books");
        return bookService.getBooks();
    }

    @GetMapping("/getBooks/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestHeader("idempotency-key") String idempotencyKey,
            @RequestBody Book book){
        return ResponseEntity.ok(bookService.addBook(book,idempotencyKey));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBook(id,book));
    }
    // checking all the books by their status
    @GetMapping
    public ResponseEntity<List<Book>> getBooksByStatus(@RequestParam BookStatus bookStatus){
        return ResponseEntity.ok(bookService.findBooksByBookStatus(bookStatus));
    }

    @GetMapping("/retry-test")
    public ResponseEntity<String> retryTest(){
        System.out.println("retry-test endpoint called");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("temporary failure");
    }

    @GetMapping("/timeOut")
    public ResponseEntity<String> timeout() throws InterruptedException{
        Thread.sleep(5000);
        return ResponseEntity.ok("Book Service responded");
    }
}
