package com.fetchpoints.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fetchpoints.dao.entity.TransactionModel;
import com.fetchpoints.dao.entity.TransactionRepository;
import com.fetchpoints.data.Balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    @Autowired
    TransactionRepository transactionRepository;

    public List<Balance> getBalances() {
        List<TransactionModel> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(transactions::add);

        // Add up all the points for each payer
        HashMap<String, Integer> balancesMap = new HashMap<String, Integer>();
        for (TransactionModel transactionModel : transactions) {
            int pointBalance = 0;
            String payer = transactionModel.getPayer();
            if (balancesMap.containsKey(payer)) {
                pointBalance = balancesMap.get(payer);
            }
            pointBalance += transactionModel.getPoints();
            balancesMap.put(payer, pointBalance);
        }

        // Convert points hashmap to an ArrayList
        ArrayList<Balance> balancesList = new ArrayList<Balance>();
        balancesMap.forEach((payer, points) -> {
            balancesList.add(new Balance(payer, points));
        });
        return balancesList;
    }

    public int getTotalBalance() {
        List<Balance> balances = getBalances();
        int total = 0;
        for (Balance balance : balances) {
            total += balance.getPoints();
        }
        return total;
    }
}
