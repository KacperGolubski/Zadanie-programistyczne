package com.stockmarket.domain;

public class Currency extends Asset{
    // Zmienne
    private double currencySpread;
    // Gettery i Settery
    public double getCurrencySpread() {
        return currencySpread;
    }
    public void setCurrencySpread(double currencySpread) {
        if(currencySpread < 0){
            throw new IllegalStateException("Spread nie może być ujemny");
        }
        this.currencySpread = currencySpread;
    }
    // Konstruktor
    public Currency(String name, String symbol, double initialPrice, double quantity, double currencySpread) {
        super(name, symbol, initialPrice, quantity, AssetType.CURRENCY);
        if(currencySpread < 0){
            throw new IllegalStateException("Spread nie może być ujemny");
        }
        this.currencySpread = currencySpread;
    }
    // Konstruktor do wczytywania aktywów z pliku
    public Currency(String name, String symbol, double currencySpread) {
        super(name, symbol, AssetType.CURRENCY);
        if(currencySpread < 0){
            throw new IllegalStateException("Spread nie może być ujemny");
        }
        this.currencySpread = currencySpread;
    }
    @Override
    public double calculateValue(){
        return (super.getCurrentPrice() - getCurrencySpread()) * super.getQuantity();
    }
    @Override
    public double calculateRevenue(double quantity) {
        return(super.getCurrentPrice() - getCurrencySpread() * quantity );
    }
}
