package com.fetchpoints.controller;

import java.util.List;

import com.fetchpoints.data.Transaction;
import com.fetchpoints.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    /**
     * @return list of all transactions
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Create a transaction. This end point accepts transaction information in JSON
     * format. It will create and send back the data to the REST customer.
     * 
     * @param transaction
     * @return newly created transaction
     */
    @PostMapping(value = "/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        final Transaction transactionData = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(transactionData, HttpStatus.CREATED);
    }

    /**
     * Delete the transaction from the system, if its id is found in the system.
     * 
     * @param id
     * @return
     */
    @DeleteMapping(value = "/transaction/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
