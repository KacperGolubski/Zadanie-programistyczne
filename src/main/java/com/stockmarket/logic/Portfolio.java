package com.stockmarket.logic;

import com.stockmarket.domain.Asset;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private double cash;

    public List<Asset> getHoldings() {return holdings;}

    public void setHoldings(List<Asset> holdings) {this.holdings = holdings;}

    private List<Asset> holdings;

    public double getCash() {
        return cash;
    }
    public void setCash(double cash) {this.cash = cash;}

    public Portfolio(int initialCash) {
        if(initialCash < 0){
            throw new IllegalArgumentException("Gotówka musi być większa od zera");
        }

        this.cash = initialCash;
        this.holdings = new ArrayList<>();
    }

    public String addAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Dodawane aktywo nie może mieć wartości null");
        }
        if (asset.getQuantity() <= 0) {
            throw new IllegalArgumentException("Liczba dodawanych aktywów nie może być mniejsza bądź równa 0");
        }
        if (holdings == null) {
            throw new IllegalArgumentException("Lista aktywów nie została utworzona");
        }
        double assetPrice = asset.getInitialPrice()*asset.getQuantity();
        if(getCash() < assetPrice) {
            throw new IllegalStateException("Za mało pieniędzy w portfelu");
        }

        for (Asset holding : holdings) {
            if (holding != null && holding.equals(asset)) {
                holding.setQuantity(holding.getQuantity() + asset.getQuantity());
                setCash(getCash() - assetPrice);
                return "Zwiększono ilość aktywa: " + holding.getName() +"\n" + "Pozostała gotówka: " + getCash();
            }
        }
        setCash(getCash() - assetPrice);
        holdings.add(asset);
        return "Dodano nowe aktywo " + asset.getName() +" do portfela" +"\n" + "Pozostała gotówka: " + getCash();

    }


    public double calculatePortfolioValue(){
        return calculateHoldingsValue() + getCash();
    }

    public double calculateHoldingsValue(){
        double totalValue = 0;
        for (Asset asset : holdings) {
            totalValue += asset.calculateValue();
        }
        return totalValue;
    }

    public double getHoldingQuantity(Asset asset){
        if(asset == null){
            throw new IllegalArgumentException("Aktywo nie może mieć wartości null");
        }
        for (Asset holding : holdings) {
            if (holding != null && holding.equals(asset)) {
                return holding.getQuantity();
            }
        } return 0;
    }
}
