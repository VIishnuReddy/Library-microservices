package com.example.library.reposiories;

import com.example.library.dtos.BookItemResponseDto;
import com.example.library.models.Book;
import com.example.library.models.BookItem;
import com.example.library.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem, Long> {
    Optional<BookItem> findByUserIdAndBook(String userId, Book book);
    Optional<BookItem> findByBarcode(String barcode);
    List<BookItem> findBookItemsByTransactionType(TransactionType transactionType);
}
