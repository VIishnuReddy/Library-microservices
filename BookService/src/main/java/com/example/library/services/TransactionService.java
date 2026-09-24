package com.example.library.services;


import com.example.library.models.Transaction;
import com.example.library.reposiories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository=transactionRepository;
    }

    public List<Transaction> getTransactionList(String userId){
        return  transactionRepository.findByUserId(userId);
    }

    public void recordTransaction(String bookName,String userId, String type, LocalDate date){
        Transaction transaction = new Transaction(bookName,userId, type, date);
        transactionRepository.save(transaction);
    }
}
