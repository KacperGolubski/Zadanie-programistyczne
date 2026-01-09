package com.stockmarket.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public abstract class Asset {
    // Zmienne
    private String name;
    private String symbol;
    private double currentPrice;
    private List<PurchaseLot> purchaseLots = new ArrayList<>();
    private AssetType assetType;

    // Gettery i Settery
    public String getSymbol() {
        return symbol;
    }
    public double getCurrentPrice() { //currentPrice to cena akcji w trakcie jej sprzedaży, trzeba ją za każdym razem zainicjować podczas testów
        return currentPrice;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
    public double getQuantity(){
        double purchaseQuantity=0;
        for (PurchaseLot purchaseLot : purchaseLots) {
            purchaseQuantity+=purchaseLot.getQuantity();
        }
        return purchaseQuantity;
    }
    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public List<PurchaseLot> getPurchaseLots() {
        return purchaseLots;
    }

    // Metody PurchaseLot
    public void mergePurchaseLot(Asset asset) {
        this.purchaseLots.addAll(asset.purchaseLots);
    }
    public void addPurchaseLot(PurchaseLot lot) {
        if (lot != null) {
            this.purchaseLots.add(lot);
        }
    }
    // Metody Asset
    public double calculateMarketValue(){
        return getQuantity()*getCurrentPrice();
    }

    public double sell (double quantityToSell) {
        if (quantityToSell > getQuantity()) {
            throw new IllegalStateException(
                    "Nie można sprzedać więcej aktywów niż posiadasz"
            );
        }
        if (getCurrentPrice() <=0 ){
            throw new IllegalStateException(
                    "Cena aktywa nie została ustawiona"
            );
        }
        double totalProfit=0;
        while (quantityToSell > 0 && !purchaseLots.isEmpty()) {
            if (purchaseLots.get(0).getQuantity() > quantityToSell) {
                purchaseLots.get(0).setQuantity(purchaseLots.get(0).getQuantity() - quantityToSell);
                totalProfit += (currentPrice - purchaseLots.get(0).getInitialPrice())*quantityToSell;
                quantityToSell = 0;
            } else if (purchaseLots.get(0).getQuantity() == quantityToSell) {
                totalProfit += (currentPrice - purchaseLots.get(0).getInitialPrice())*quantityToSell;
                purchaseLots.remove(0);
                quantityToSell = 0;
            } else if (purchaseLots.get(0).getQuantity() < quantityToSell) {
                totalProfit += (currentPrice - purchaseLots.get(0).getInitialPrice())*purchaseLots.get(0).getQuantity();
                quantityToSell -= purchaseLots.get(0).getQuantity();
                purchaseLots.remove(0);
            }
        }
        return totalProfit;
    }
    private void validateAssetData(String name, String symbol){
        //walidacja nazwy
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Nazwa aktywa nie może być pusta albo null");
        }
        if(isMissingLetters(name)){
            throw new IllegalArgumentException("Nazwa aktywa musi zawierać przynajmniej jedną literę");
        }
        if(name.length()<2 || name.length()>30){
            throw new IllegalArgumentException("Nazwa musi mieć conajmniej 2 znaki i nie może dłuższa niż 30 znaków");
        }
        if(symbol == null || symbol.trim().isEmpty()){
            throw new IllegalArgumentException("Symbol aktywa nie może być pusty albo null");
        }
        //walidacja symbolu
        if(isMissingLetters(symbol)){
            throw new IllegalArgumentException("Symbol aktywa musi zawierać przynajmniej jedną literę");
        }

        if(symbol.length()>5){
            throw new IllegalArgumentException("Symbol nie może być dłuższy niż 5 znaków");
        }
    }

    private void validateAssetNumericData(double initialPrice, double quantity){
        //walidacja ceny początkowej
        if(initialPrice <= 0){
            throw new IllegalArgumentException("Cena aktywa musi być liczbą dodatnią");
        }
        //walidacja ilości
        if(quantity <= 0){
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }
    }
    // Metoda abstrakcyjna
    public abstract double calculateValue();
    public abstract double calculateRevenue(double quantity);
    //Konstruktor Asset
    public Asset(String name, String symbol, double initialPrice, double quantity, AssetType assetType) {
        validateAssetData(name,symbol);
        validateAssetNumericData(initialPrice,quantity);
        this.assetType = assetType;
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = 0; // Pamiętać o setCurrentPrice przy tworzeniu aktywów
        this.purchaseLots.add(new PurchaseLot(initialPrice, quantity, LocalDateTime.now()));
    }

    //Konstruktor Asset do wczytywania aktywów z pliku
    public Asset(String name, String symbol, AssetType assetType) {
        validateAssetData(name,symbol);
        this.name = name;
        this.symbol = symbol;
        this.assetType = assetType;
        this.currentPrice = 0;
    }

    @Override
    public String toString(){
        return String.format("%-10s | %-15s | %-5s | Ilość: %8.2f | Wartość: %10.2f PLN",
                getAssetType(), getName(), getSymbol(), getQuantity(), calculateMarketValue());
    }

    private boolean isMissingLetters(String string){
        for(char c : string.toCharArray()){
            if(Character.isLetter(c)){
                return false;
            }
        } return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(symbol, asset.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(symbol);
    }

}


