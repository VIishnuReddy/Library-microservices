package com.example.library.controllers;

import com.example.library.dtos.BookItemResponseDto;
import com.example.library.models.BookItem;
import com.example.library.models.BookStatus;
import com.example.library.models.TransactionType;
import com.example.library.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookItem")
public class BookItemController {

    private BookService bookService;

    public BookItemController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{type}/{userId}/{bookId}")
    public ResponseEntity<BookItem> handleTransaction(
            @PathVariable TransactionType type,
            @PathVariable String userId,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.handleTransaction(type, userId, bookId));
    }

    @PostMapping("/return/{barcode}")
    public String returnBook(@PathVariable String barcode) {
        return bookService.returnBook(barcode);
    }

    @GetMapping
    public ResponseEntity<List<BookItem>> findBooksByTransactionType
            (@RequestParam TransactionType transactionType){
        return ResponseEntity.ok(bookService.getBooksByTransactionType(transactionType));
    }

}