package com.example.library.factories;

import com.example.library.models.Book;
import com.example.library.models.BookItem;
import com.example.library.models.TransactionType;


import java.time.LocalDate;
import java.util.UUID;

public class BookItemFactory {
    public static BookItem create(Book book, String userId, TransactionType type) {
        BookItem item = new BookItem();
        item.setBook(book);
        item.setUserId(userId);
        item.setBarcode(UUID.randomUUID().toString());
        item.setBorrowDate(LocalDate.now());
        item.setTransactionType(type);
        return item;
    }
}
