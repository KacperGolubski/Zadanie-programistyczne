package com.stockmarket;

import java.util.Objects;

public class Stock {
    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    private String name;
    private String symbol;
    private Double initialPrice;

    public Stock(String name, String symbol, Double initialPrice) {
        this.name = name;
        this.symbol = symbol;
        this.initialPrice = initialPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(symbol);
    }
}
