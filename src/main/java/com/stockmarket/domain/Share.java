package com.stockmarket.domain;

public class Share extends Asset{
    // Zmienne
    private double fee = 5.00;
    // Gettery i Settery
    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        if(fee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.fee = fee;
    }
    // Konstruktor
    public Share(String name, String symbol, double initialPrice, double quantity){
        super(name, symbol, initialPrice, quantity, AssetType.SHARE);
    }
    // Konstruktor do wczytywania aktywów z pliku
    public Share(String name, String symbol, double fee){
        super(name, symbol, AssetType.SHARE);
        if(fee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.fee = fee;
    }

    @Override
    public double calculateValue() {
        return getCurrentPrice()*super.getQuantity() - getFee();
    }
    @Override
    public double calculateRevenue(double quantity) {
        return super.getCurrentPrice()*quantity - getFee();
    }
}
