package com.stockmarket.domain;

public class Share extends Asset{
    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        if(fee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.fee = fee;
    }

    private double fee = 5.00;

    public Share(String name, String symbol, double initialPrice, double quantity){
        super(name, symbol, initialPrice, quantity);
    }

    @Override
    public double calculateValue() {
        return getInitialPrice() * getQuantity() - getFee();
    }
}
