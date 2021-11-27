package com.fetchpoints.data;

public class Balance {

    private String payer;
    private int points;

    public Balance(String payer, int points) {
        this.payer = payer;
        this.points = points;
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
}
