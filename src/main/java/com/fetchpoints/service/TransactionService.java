package com.fetchpoints.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fetchpoints.dao.entity.TransactionModel;
import com.fetchpoints.dao.entity.TransactionRepository;
import com.fetchpoints.data.Transaction;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction createTransaction(final Transaction transaction) {
        TransactionModel transactionModel = new TransactionModel(transaction);

        transactionModel = transactionRepository.save(transactionModel);
        Transaction transactionData = new Transaction();
        BeanUtils.copyProperties(transactionModel, transactionData);

        return transactionData;
    }

    public List<Transaction> getTransactions() {
        List<TransactionModel> transactions = new ArrayList<>();
        transactionRepository.findAll(Sort.by(Sort.Direction.ASC, "timestamp")).forEach(transactions::add);

        List<Transaction> transactionList = new ArrayList<>();
        for (TransactionModel transactionModel : transactions) {
            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionModel, transaction);
            transactionList.add(transaction);
        }

        return transactionList;
    }

    public Transaction getTransaction(Long id) {

        Optional<TransactionModel> transaction = transactionRepository.findById(id);
        Transaction transactionData = new Transaction();
        BeanUtils.copyProperties(transaction.get(), transactionData);
        return transactionData;
    }

    public void updateTransaction(final Transaction transaction) {
        TransactionModel transactionModel = new TransactionModel(transaction, true);

        transactionModel = transactionRepository.save(transactionModel);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
