package com.stockmarket.domain;

public class Currency extends Asset{
    public double getCurrencySpread() {
        return currencySpread;
    }

    public void setCurrencySpread(double currencySpread) {
        if(currencySpread < 0){
            throw new IllegalStateException("Spread nie może być ujemny");
        }
        this.currencySpread = currencySpread;
    }

    private double currencySpread;
    public Currency(String name, String symbol, double initialPrice, double quantity, double currencySpread) {
        super(name, symbol, initialPrice, quantity);
        if(currencySpread < 0){
            throw new IllegalStateException("Spread nie może być ujemny");
        }
        this.currencySpread = currencySpread;
    }
    @Override
    public double calculateValue(){
        return (getInitialPrice() - getCurrencySpread()) * getQuantity();
    }
}
