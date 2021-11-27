package com.fetchpoints.controller;

import java.util.List;

import com.fetchpoints.data.SpendPointsReceipt;
import com.fetchpoints.data.SpendPointsRequest;
import com.fetchpoints.service.SpendPointsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/spend-points")
public class SpendPointsController {

    @Autowired
    SpendPointsService spendPointsService;

    @PostMapping
    public ResponseEntity<List<SpendPointsReceipt>> spendPoints(@RequestBody SpendPointsRequest spendPointsRequest) {
        final List<SpendPointsReceipt> spendPointsReceiptData = spendPointsService.spendPoints(spendPointsRequest);
        return new ResponseEntity<>(spendPointsReceiptData, HttpStatus.OK);
    }
}
