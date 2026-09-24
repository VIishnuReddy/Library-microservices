package com.example.library.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class BookItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Book book;
    private String userId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String barcode;
    private LocalDate borrowDate;
}
