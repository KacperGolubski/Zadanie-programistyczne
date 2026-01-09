package com.stockmarket.domain;

public class Commodity extends Asset{
    // Zmienne
    private double commodityFee;
    // Gettery i Settery
    public double getCommodityFee() {
        return commodityFee;
    }
    public void setCommodityFee(double commodityFee) {
        if(commodityFee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.commodityFee = commodityFee;
    }
    // Konstruktor
    public Commodity(String name, String symbol, double initialPrice, double quantity, double commodityFee)
    {
        super(name, symbol, initialPrice, quantity, AssetType.COMMODITY);
        if(commodityFee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.commodityFee = commodityFee;
    }
    // Konstruktor do wczytywania aktywów z pliku
    public Commodity(String name, String symbol, double commodityFee){
        super(name, symbol, AssetType.COMMODITY);
        if(commodityFee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.commodityFee = commodityFee;
    }

    @Override
    public double calculateValue()
    {
        return super.getQuantity() * super.getCurrentPrice() - (super.getQuantity() * getCommodityFee());
    }
    @Override
    public double calculateRevenue(double quantity) {
        return quantity*super.getCurrentPrice() - (quantity * getCommodityFee());
    }
}
