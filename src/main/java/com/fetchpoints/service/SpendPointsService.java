package com.fetchpoints.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fetchpoints.data.Balance;
import com.fetchpoints.data.SpendPointsReceipt;
import com.fetchpoints.data.SpendPointsRequest;
import com.fetchpoints.data.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpendPointsService {

    @Autowired
    TransactionService transactionService;

    @Autowired
    BalanceService balanceService;

    public List<SpendPointsReceipt> spendPoints(SpendPointsRequest spendPointsRequest) {

        List<Transaction> transactions = getNonRetiredTransactions();

        HashMap<String, Integer> balancesMap = getBalancesMap();
        HashMap<String, Integer> receiptMap = new HashMap<String, Integer>();
        int pointsToSpend = spendPointsRequest.getPoints();

        // In the case that the amount to spend isn't available, return and spend
        // nothing
        if (pointsToSpend > balanceService.getTotalBalance()) {
            return new ArrayList<SpendPointsReceipt>();
        }

        // Starting at the start of the list, remove transactions from the database
        // until the number of points to spend is 0
        for (Transaction transaction : transactions) {
            if (transaction.getRetired()) {
                continue;
            }

            if (pointsToSpend <= 0) {
                break;
            }

            String payer;
            int points;
            int payersBalance;
            int pointsSpentFromTransaction;

            payer = transaction.getPayer();
            points = transaction.getPoints();
            payersBalance = balancesMap.get(payer);

            // Spend points
            if (pointsToSpend < points) {
                pointsSpentFromTransaction = pointsToSpend;

                // Alter the number of points left in the transaction
                transaction.setPoints(points - pointsToSpend);
                pointsToSpend = 0;
            } else {
                pointsSpentFromTransaction = points;

                // Remove the transaction
                pointsToSpend -= points;
                payersBalance -= points;
                balancesMap.put(payer, payersBalance);
                transaction.setPoints(0);
                transaction.setRetired(true);
            }
            transactionService.updateTransaction(transaction);

            addPointsToReceipt(receiptMap, payer, pointsSpentFromTransaction);
        }

        return convertHashMapToArrayList(receiptMap, transactions);
    }

    private List<Transaction> getNonRetiredTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();

        int i;
        for (i = 0; i < transactions.size(); i++) {
            if (!transactions.get(i).getRetired()) {
                break;
            }
        }
        return transactions.subList(i, transactions.size());
    }

    private HashMap<String, Integer> getBalancesMap() {
        List<Balance> balances = balanceService.getBalances();
        HashMap<String, Integer> balancesMap = new HashMap<String, Integer>();
        balances.forEach(balance -> {
            balancesMap.put(balance.getPayer(), balance.getPoints());
        });
        return balancesMap;
    }

    private void addPointsToReceipt(HashMap<String, Integer> receiptMap, String payer, int pointsSpent) {

        int pointBalance = 0;
        if (receiptMap.containsKey(payer)) {
            pointBalance = receiptMap.get(payer);
        }
        pointBalance -= pointsSpent;
        receiptMap.put(payer, pointBalance);
    }

    private ArrayList<SpendPointsReceipt> convertHashMapToArrayList(HashMap<String, Integer> hashMap,
            List<Transaction> transactions) {

        ArrayList<SpendPointsReceipt> receiptList = new ArrayList<SpendPointsReceipt>();
        int numberOfReceipts = hashMap.size();

        for (Transaction transaction : transactions) {
            if (numberOfReceipts <= 0)
                break;

            String payer = transaction.getPayer();
            int payersPoints = hashMap.get(payer);

            if (receiptListContainsPayer(receiptList, payer)) {
                continue;
            } else {
                receiptList.add(new SpendPointsReceipt(payer, payersPoints));
                numberOfReceipts--;
            }
        }
        return receiptList;
    }

    private boolean receiptListContainsPayer(ArrayList<SpendPointsReceipt> receiptList, String payer) {
        for (SpendPointsReceipt receipt : receiptList) {
            if (payer.equals(receipt.getPayer())) {
                return true;
            }
        }
        return false;
    }
}
