package com.stockmarket.domain;

public class Commodity extends Asset{

    public double getCommodityFee() {
        return commodityFee;
    }

    public void setCommodityFee(double commodityFee) {
        if(commodityFee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.commodityFee = commodityFee;
    }

    private double commodityFee;

    public Commodity(String name, String symbol, double initialPrice, double quantity, double commodityFee)
    {
        super(name, symbol, initialPrice, quantity);
        if(commodityFee < 0){
            throw new IllegalArgumentException("Opłata nie może być ujemna");
        }
        this.commodityFee = commodityFee;
    }

    @Override
    public double calculateValue()
    {
        return getQuantity() * getInitialPrice() - (getQuantity() * getCommodityFee());
    }
}
