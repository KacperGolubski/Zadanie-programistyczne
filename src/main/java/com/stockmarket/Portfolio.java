package com.stockmarket;

public class Portfolio {
    private static class StockHolding {
        Stock stock;
        int quantity;

        public StockHolding(Stock stock, int quantity) {
            this.stock = stock;
            this.quantity = quantity;
        }
    }

    private double cash;
    private StockHolding [] holdings;
    private int holdingsCount;

    public double getCash() {
        return cash;
    }

    public StockHolding[] getHoldings() {
        return holdings;
    }

    public int getHoldingsCount() {
        return holdingsCount;
    }


    public Portfolio(int initialCash) {
        //walidacja gotówki
        if(initialCash < 0){
            throw new IllegalArgumentException("Gotówka musi być większa od zera");
        }

        this.cash = initialCash;
        this.holdings = new StockHolding[10];
        this.holdingsCount = 0;
    }

    public void addStock(Stock stock, int quantity) {
        if(stock == null){
            throw new IllegalArgumentException("Dodawana akcja nie może mieć wartości null");
        }
        if(quantity <= 0){
            throw new IllegalArgumentException("Liczba dodawanych akcji nie może być mniejsza bądź równa 0");
        }

        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i]!=null && holdings[i].stock!=null && holdings[i].stock.equals(stock)) {
                holdings[i].quantity += quantity;
                return;
            }
        }
            if (holdingsCount < holdings.length) {
                holdings[holdingsCount] = new StockHolding(stock, quantity);
                holdingsCount++;
            } else if  (holdingsCount == holdings.length) {
                throw new IllegalStateException("Nie można dodać więcej typów akcji");
            }
    }

    public double calculateStockValue(){
        double value=0;
        for (int i = 0; i < holdingsCount; i++) {
            if(holdings[i]!=null && holdings[i].stock!=null) {
                value += holdings[i].stock.getInitialPrice() * holdings[i].quantity;
            }
        }
        return value;
    }

    public double calculateTotalValue(){
        return calculateStockValue() + getCash();
    }

    public int getStockQuantity(Stock stock){
        if(stock == null){
            throw new IllegalArgumentException("Akcja nie może mieć wartości null");
        }

        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i]!=null && holdings[i].stock!=null && holdings[i].stock.equals(stock)) {
                return holdings[i].quantity;
            }
        } return 0;
    }
}
