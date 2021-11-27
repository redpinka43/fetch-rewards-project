package com.fetchpoints.controller;

import java.util.List;

import com.fetchpoints.data.Balance;
import com.fetchpoints.service.BalanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    /**
     * @return list of payer balances
     */
    @GetMapping
    public ResponseEntity<List<Balance>> getBalances() {
        List<Balance> balances = balanceService.getBalances();
        return new ResponseEntity<>(balances, HttpStatus.OK);
    }
}
