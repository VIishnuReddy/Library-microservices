package com.example.library.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private String type;
    private String userId;
    private LocalDate date;


    public Transaction(String bookName,String userId, String type, LocalDate date) {
        this.bookName = bookName;
        this.userId=userId;
        this.type = type;
        this.date=date;
    }
}
