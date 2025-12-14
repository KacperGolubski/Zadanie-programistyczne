package com.stockmarket.domain;

import java.util.Objects;

public abstract class Asset {

    public String getSymbol() {

        return symbol;
    }

    public Double getInitialPrice() {

        return initialPrice;
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

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    private String name;
    private String symbol;
    private double initialPrice;
    private double quantity;


    public Asset(String name, String symbol, double initialPrice, double quantity) {
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

        //walidacja symbolu
        if(symbol == null || symbol.trim().isEmpty()){
            throw new IllegalArgumentException("Symbol aktywa nie może być pusty albo null");
        }

        if(isMissingLetters(symbol)){
            throw new IllegalArgumentException("Symbol aktywa musi zawierać przynajmniej jedną literę");
        }

        if(symbol.length()>5){
            throw new IllegalArgumentException("Symbol nie może być dłuższy niż 5 znaków");
        }
        //walidacja ceny początkowej
        if(initialPrice <= 0){
            throw new IllegalArgumentException("Cena aktywa musi być liczbą dodatnią");
        }

        //walidacja ilości
        if(quantity <= 0){
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }

        this.name = name;
        this.symbol = symbol;
        this.initialPrice = initialPrice;
        this.quantity = quantity;
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

    public abstract double calculateValue();

    private boolean isMissingLetters(String string){
        for(char c : string.toCharArray()){
            if(Character.isLetter(c)){
                return false;
            }
        } return true;
    }
}
