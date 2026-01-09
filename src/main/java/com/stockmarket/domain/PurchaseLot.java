package com.stockmarket.domain;
import java.time.LocalDateTime;

public class PurchaseLot {
    // Zmienne
    private double initialPrice;
    private double quantity;
    private LocalDateTime date;

    // Gettery i Settery
    public double getInitialPrice() {
        return initialPrice;}
    public void setInitialPrice(double initialPrice) {
        if(initialPrice <= 0){
            throw new IllegalArgumentException("Cena aktywa musi być liczbą dodatnią");
        }
        this.initialPrice = initialPrice;}
    public double getQuantity() {
        return quantity;}
    public void setQuantity(double quantity) {
        if(quantity < 0){
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }
        this.quantity = quantity;}
    public LocalDateTime getDate() {
        return date;}
    public void setDate(LocalDateTime date) {
        if(date == null){
            throw new IllegalArgumentException("Data zakupu nie może być NULL");
        }
        this.date = date;}
    // Konstruktor
    public PurchaseLot(double initialPrice, double quantity, LocalDateTime date) {
        if(quantity <= 0){
            throw new IllegalArgumentException("Ilość musi być większa od zera");
        }
        if(initialPrice <= 0){
            throw new IllegalArgumentException("Cena aktywa musi być liczbą dodatnią");
        }
        if(date == null){
            throw new IllegalArgumentException("Data zakupu nie może być NULL");
        }

        this.initialPrice = initialPrice;
        this.quantity = quantity;
        this.date = date;
    }
}
