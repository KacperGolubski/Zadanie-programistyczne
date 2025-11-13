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
        //walidacja nazwy
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Nazwa akcji nie może być pusta albo null");
        }
        if(isMissingLetters(name)){
            throw new IllegalArgumentException("Nazwa akcji musi zawierać przynajmniej jedną literę");
        }
        if(name.length()<2 || name.length()>30){
            throw new IllegalArgumentException("Nazwa musi mieć conajmniej 2 znaki i nie może dłuższa niż 30 znaków");
        }

        //walidacja symbolu
        if(symbol == null || symbol.trim().isEmpty()){
            throw new IllegalArgumentException("Symbol akcji nie może być pusty albo null");
        }

        if(isMissingLetters(symbol)){
            throw new IllegalArgumentException("Symbol akcji musi zawierać przynajmniej jedną literę");
        }

        if(symbol.length()>5){
            throw new IllegalArgumentException("Symbol nie może być dłuższy niż 5 znaków");
        }
        //walidacja ceny początkowej
        if(initialPrice == null || initialPrice <= 0){
            throw new IllegalArgumentException("Cena akcji musi być liczbą dodatnią");
        }

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

    private boolean isMissingLetters(String string){
        for(char c : string.toCharArray()){
            if(Character.isLetter(c)){
                return false;
            }
        } return true;
    }
}
