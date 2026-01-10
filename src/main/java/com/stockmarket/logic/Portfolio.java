package com.stockmarket.logic;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.PurchaseLot;

import java.util.*;


public class Portfolio {
    //Zmienne
    private double cash;
    private Set<String> watchList = new HashSet<>();
    private PriorityQueue<Order> orderQueue = new PriorityQueue<>();
    private Map<String, Asset> holdings;
    //Gettery i Settery
    public void setHoldings(Map<String, Asset> holdings) {
        this.holdings = holdings;
    }
    public Map<String, Asset> getHoldings() {
        return holdings;
    }
    public double getCash() {
        return cash;
    }
    public void setCash(double cash) {
        this.cash = cash;
    }
    //Konstruktor Portfolio
    public Portfolio(double initialCash) {
        if(initialCash < 0){
            throw new IllegalArgumentException("Gotówka musi być większa od zera");
        }

        this.cash = initialCash;
        this.holdings = new HashMap<>();
    }
    //Metody Portfolio
    public String buyAsset(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Dodawane aktywo nie może mieć wartości null");
        }
        if (asset.getQuantity() <= 0) {
            throw new IllegalArgumentException("Liczba dodawanych aktywów nie może być mniejsza bądź równa 0");
        }
        if (holdings == null) {
            throw new IllegalArgumentException("Lista aktywów nie została utworzona");
        }
        double purchaseCost = 0;
        for (PurchaseLot lot : asset.getPurchaseLots()) {
            purchaseCost += lot.getQuantity() * lot.getInitialPrice();
        }
        if(getCash() < purchaseCost) {
            throw new IllegalStateException("Za mało pieniędzy w portfelu");
        }
        if (holdings.containsKey(asset.getSymbol())) {
            Asset existingAsset = holdings.get(asset.getSymbol());
            if(existingAsset.getAssetType() != asset.getAssetType()) {
                throw new IllegalArgumentException(
                        "Portfel zawiera już aktywa z tym samym symbolem");
            }
            existingAsset.mergePurchaseLot(asset);
            setCash(getCash() - purchaseCost);
            return "Zwiększono ilość aktywa: " + asset.getName() +"\n" + "Pozostała gotówka: " + getCash();
            }

        setCash(getCash() - purchaseCost);
        holdings.put(asset.getSymbol(), asset);
        return "Dodano nowe aktywo " + asset.getName() +" do portfela" +"\n" + "Pozostała gotówka: " + getCash();

    }
    public String sellAsset(String symbol, double quantityToSell) {
        if (!holdings.containsKey(symbol)) {
            throw new IllegalArgumentException("Nie posiadasz aktywa o symbolu: " + symbol);
        }
        Asset asset = holdings.get(symbol);
        double revenue = asset.calculateRevenue(quantityToSell);
        double profitBrutto = asset.sell(quantityToSell);
        double transactionValue = quantityToSell * asset.getCurrentPrice();
        double transactionCost = transactionValue -  revenue;
        double profitNetto = profitBrutto - transactionCost;
        setCash(getCash() + revenue);
        if (asset.getQuantity() == 0) {
            holdings.remove(symbol);
        }
        if(profitNetto>0){
            return "Aktywa sprzedano z zyskiem: " + profitNetto;
        }
        else if(profitNetto<0){
            return "Aktywa sprzedano ze stratą: " + profitNetto;
        }
        else{
            return "Aktywa sprzeda bez straty i zysku";
        }
    }

    public double calculatePortfolioValue() {
        return calculateHoldingsValue() + getCash();
    }

    public double calculateHoldingsValue(){
        double totalValue = 0;
        for (Asset asset : holdings.values()) {
            totalValue += asset.calculateValue();
        }
        return totalValue;
    }

    public double getHoldingQuantity(Asset asset){
        if(asset == null){
            throw new IllegalArgumentException("Aktywo nie może mieć wartości null");
        }

        if(holdings.get(asset.getSymbol()) != null){
            return asset.getQuantity();
        }
        return 0;
    }

    // Generator raportu
    public ArrayList<Asset> assetSorter(){
        ArrayList<Asset> sortedAssets = new ArrayList<>(holdings.values());
        AssetComparator comparator = new AssetComparator();
        sortedAssets.sort(comparator);
        return sortedAssets;
    }

    public List<String> printReport(){
        List<String> report = new ArrayList<>();
        ArrayList<Asset> sortedAssets = assetSorter();
        for (Asset asset : sortedAssets) {
            report.add(asset.toString());
        }
        return report;
    }

    // Order
    public void addOrder(Order order) {
        orderQueue.add(order);
    }
    public List<String> printOrder(){
        List<String> report = new ArrayList<>();
        PriorityQueue<Order> copy = new PriorityQueue<>(this.orderQueue);
        while (!copy.isEmpty()){
            report.add(copy.poll().toString());
        }
        return report;

    }
    // WatchList
    public void addToWatchList(Asset asset) {
        if(asset == null){
            throw new IllegalArgumentException("Asset nie może być null");
        }
        watchList.add(asset.toString());
    }
    public List<String> printWatchList(){
        List<String> report = new ArrayList<>();
        if (watchList.isEmpty()) {
            throw new IllegalArgumentException("Lista jest pusta");
        }
        return new ArrayList<>(watchList);
    }


}
