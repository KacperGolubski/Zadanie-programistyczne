package com.stockmarket;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.Commodity;
import com.stockmarket.domain.PurchaseLot;
import com.stockmarket.domain.Share;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PurchaseLotTest {
    @Test
    void shouldThrowExceptionWhenInitialPriceIsLessThanZero() {
        Asset commodity = new Commodity("Gold", "GLD", 12500.0, 10, 10);
        PurchaseLot lot = commodity.getPurchaseLots().get(0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lot.setInitialPrice(-1);
        });
        assertEquals("Cena aktywa musi być liczbą dodatnią",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenQuantityIsNegative(){
        Asset commodity = new Commodity("Gold", "GLD", 12500.0, 10, 10);
        PurchaseLot lot = commodity.getPurchaseLots().get(0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lot.setQuantity(-1);
            //portfolio.buyAsset(shareCDR);
        });
        assertEquals("Ilość musi być większa od zera",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenDateIsNULL(){
        Asset commodity = new Commodity("Gold", "GLD", 12500.0, 10, 10);
        PurchaseLot lot = commodity.getPurchaseLots().get(0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lot.setDate(null);
        });
        assertEquals("Data zakupu nie może być NULL",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenInitialPriceIsLessThanZeroInConstructor(){
        Asset share = new Share("CD Project", "CDP", 5.00);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PurchaseLot lot = new PurchaseLot(-1, 10, LocalDateTime.now());
        });
        assertEquals("Cena aktywa musi być liczbą dodatnią",   exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenQuantityIsLessThanZeroInConstructor(){
        Asset share = new Share("CD Project", "CDP", 5.00);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PurchaseLot lot = new PurchaseLot(100, -1, LocalDateTime.now());
        });
        assertEquals("Ilość musi być większa od zera",   exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenDateIsNULLInConstructor(){
        Asset share = new Share("CD Project", "CDP", 5.00);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PurchaseLot lot = new PurchaseLot(150, 10, null);
        });
        assertEquals("Data zakupu nie może być NULL",   exception.getMessage());
    }
}
