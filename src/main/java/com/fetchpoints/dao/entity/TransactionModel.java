package com.fetchpoints.dao.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fetchpoints.data.Transaction;

import org.springframework.beans.BeanUtils;

@Entity
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String payer;
    private int points;
    private boolean retired;
    private LocalDateTime timestamp;

    public TransactionModel() {
    }

    public TransactionModel(Transaction transaction) {
        this(transaction, false);
    }

    public TransactionModel(Transaction transaction, boolean copyId) {
        BeanUtils.copyProperties(transaction, this);
        if (copyId) {
            this.id = transaction.getId();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getPayer() {
        return this.payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean getRetired() {
        return this.retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
