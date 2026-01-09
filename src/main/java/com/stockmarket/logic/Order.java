package com.stockmarket.logic;

import com.stockmarket.domain.TransactionType;

public class Order implements Comparable<Order> {
    // Zmienne
    private String name;
    private String symbol;
    private TransactionType transactionType;
    private double quantity;
    private double priceLimit;
    // Gettery i Settery
    public String  getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    public double getOrderQuantity() {
        return quantity;
    }
    public void setOrderQuantity(double quantity) {
        this.quantity = quantity;
    }
    public double getPriceLimit() {
        return priceLimit;
    }
    public void setPriceLimit(double priceLimit) {
        this.priceLimit = priceLimit;
    }
    // Konstruktor
    public Order(String name, String symbol, TransactionType transactionType, double quantity, double priceLimit) {
        this.name = name;
        this.symbol = symbol;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.priceLimit = priceLimit;
    }
    @Override
    public String toString(){
        return String.format("%-10s | %-15s | %-5s | Ilość: %8.2f | Wartość: %10.2f PLN",
                getTransactionType(), getName(), getSymbol(), getOrderQuantity(), getPriceLimit());
    }
    @Override
    public int compareTo(Order other){
        if(this.transactionType == TransactionType.BUY){
            return Double.compare(other.priceLimit, this.priceLimit);
        }
        else if(this.transactionType == TransactionType.SELL)
        {return Double.compare(this.priceLimit, other.priceLimit);
        }
        else
        {throw new IllegalArgumentException("Niedozwolony typ transakcji (BUY/SELL)");}
    }
}

