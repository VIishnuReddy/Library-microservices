package com.example.library.dtos;

import com.example.library.models.Book;
import com.example.library.models.TransactionType;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
public class BookItemResponseDto {

    private Book book;
    private String userId;
    private TransactionType transactionType;
    private String barcode;
    private LocalDate borrowDate;
}
