package com.stockmarket;

import com.stockmarket.domain.*;
import com.stockmarket.logic.Portfolio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssetTest {

    @Test
    void TestNameGetter(){
        Asset testShare = new Share("CD Projekt", "CDP", 100.00, 30);

        assertEquals("CD Projekt", testShare.getName(), "Nazwa aktywa nie zgadza się z oczekiwaną wartością");
    }
    @Test
    void TestSymbolGetter(){
        Asset testShare = new Share("CD Projekt", "CDP", 100.00, 20);

        assertEquals("CDP", testShare.getSymbol(), "Symbol aktywa nie zgadza się z oczekiwaną wartością");
    }
    @Test
    void TestPriceGetter(){
        Asset testShare = new Share("CD Projekt", "CDP", 100.00, 20);
        testShare.setCurrentPrice(100.00);
        assertEquals(100, testShare.getCurrentPrice(), "Cena aktywa nie zgadza się z oczekiwaną wartością");
    }
    @Test
    void TestQuantityGetter(){
        Asset testShare = new Share("CD Projekt", "CDP", 100.00, 20);

        assertEquals(20, testShare.getQuantity());
    }
    @Test
    void shouldThrowExceptionWhenInitialPriceIsZero() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testShare = new Share("Testowa Firma", "TEST", 0.0, 10);
        });

        assertEquals("Cena aktywa musi być liczbą dodatnią", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenInitialPriceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testShare = new Share("Testowa Firma", "TEST", -10.0, 3);
        });

        assertEquals("Cena aktywa musi być liczbą dodatnią",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenInitialPriceIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testShare = new Share("Testowa Firma", "TEST", 30, 0);
        });

        assertEquals("Ilość musi być większa od zera", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testShare = new Share(" ", "TEST", 100.0, 1);
        });

        assertEquals("Nazwa aktywa nie może być pusta albo null",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share(null, "TEST", 100.0, 1);
        });

        assertEquals("Nazwa aktywa nie może być pusta albo null",   exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share("Test", " ", 100.0,1);
        });

        assertEquals("Symbol aktywa nie może być pusty albo null",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share("Test", null, 100.0, 1);
        });

        assertEquals("Symbol aktywa nie może być pusty albo null",    exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameHasNoLetters() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share("@@@@", "CDP", 100.0, 5);
        });

        assertEquals("Nazwa aktywa musi zawierać przynajmniej jedną literę",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolHasNoLetters() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share("CDP", "1234", 100.0, 1);
        });

        assertEquals("Symbol aktywa musi zawierać przynajmniej jedną literę",   exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolLengthIsTooLong() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share("Test", "1234p6", 100.0, 1);
        });

        assertEquals("Symbol nie może być dłuższy niż 5 znaków", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameLengthIsTooShort() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share("P", "test", 100.0, 1);
        });

        assertEquals("Nazwa musi mieć conajmniej 2 znaki i nie może dłuższa niż 30 znaków", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameLengthIsTooLong() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset testshare = new Share("T12345678901234567890123456789T", "test", 100.0, 20);
        });

        assertEquals("Nazwa musi mieć conajmniej 2 znaki i nie może dłuższa niż 30 znaków", exception.getMessage());
    }
    @Test
    void shouldAllowNameWithExactly30Characters() {
        assertDoesNotThrow(() -> {
            Asset testshare = new Share("ToJestBardzoDługaNazwaFirmy30", "test", 100.0, 20);
        });
    }
    @Test
    void shouldAllowSymbolWithExactly5Characters() {
        assertDoesNotThrow(() -> {
            Asset testshare = new Share("Test", "Test0", 100.0, 1);
        });
    }
    @Test
    void shouldAllowPositiveInitialPrice(){
        assertDoesNotThrow(() -> {
            Asset testshare = new Share("Test", "Test", 0.01, 1);
        });
    }
    @Test
    void shouldBeEqualWhenSymbolsAreEqual(){
        Asset asset1 = new Share("CD Project", "CDP", 100.0, 1);
        Asset asset2 = new Share("CD Project Red", "CDP", 100.0, 1);

        assertTrue(asset1.equals(asset2));
    }
    @Test
    void shouldNotBeEqualWhenSymbolsAreNotEqual(){
        Asset asset1 = new Currency("CD Project", "CDP", 100.0, 10, 5);
        Asset asset2 = new Currency("CD Project", "CDP2", 100.0, 10, 5);

        assertFalse(asset1.equals(asset2));
    }
    @Test
    void shouldBeEqualWhenSymbolsAreEqualHashCode(){
        Asset asset1 = new Commodity("CD Project", "CDP", 100.0, 30, 10);
        Asset asset2 = new Commodity("CD Project Red", "CDP", 100.0, 20, 5);
        assertEquals(asset1.hashCode(), asset2.hashCode());
    }
    @Test
    void shouldNotBeEqualWhenSymbolIsNull() {
        Asset asset1 = new Share("CD Project", "CDP", 100.0, 40);
        assertFalse(asset1.equals(null));
    }
    @Test
    void differentAssetsShouldNotBeNullEqual(){
        Asset asset1 = new Share("CD Project", "CDP", 100.0, 10);
        Asset asset2 = new Commodity("CD Project Red", "CDP", 100.0, 20, 5);
        assertFalse(asset1.equals(asset2));
    }
    @Test
    void shouldClassNotBeEqual(){
        Asset asset1 = new Share("CD Project", "CDP", 100.0, 30);
        class TestClass{
            String name;
            String symbol;
            double initialPrice;
            int quantity;

        }
        assertFalse(asset1.equals(new TestClass()));
    }
    @Test
    void shouldReturnCorrectValueShare(){
        Asset share = new Share("CD Project", "CDP", 100.0, 10);
        share.setCurrentPrice(100.0);
        assertEquals(995, share.calculateValue());
    }
    @Test
    void shouldReturnCorrectValueCommodity(){
        Asset commodity = new Commodity("Gold", "GLD", 100.0, 10, 5);
        commodity.setCurrentPrice(100.0);
        assertEquals(950, commodity.calculateValue());
    }
    @Test
    void shouldReturnCorrectValueCurrency(){
        Asset currency = new Currency("Euro", "EUR", 100.0, 10, 5);
        currency.setCurrentPrice(100.0);
        assertEquals(950, currency.calculateValue());
    }
    @Test
    void shouldThrowExceptionWhenQuantityIsZeroOrLess(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Asset share = new Share("Test", "TST", 100, 0);
        });
        assertEquals("Ilość musi być większa od zera",   exception.getMessage());
    }
    @Test
    void shouldSetCorrectQuantity(){
        Asset share = new Share("CD Project", "CDP", 100.0, 1);
        PurchaseLot lot = share.getPurchaseLots().get(0);
        lot.setQuantity(100);
        assertEquals(100, share.getQuantity());
    }
    @Test
    void shouldSetCorrectInitialPrice(){
        Asset commodity = new Commodity("Gold", "GLD", 1.0, 10, 10);
        commodity.setCurrentPrice(12000.0);
        assertEquals(12000.0, commodity.getCurrentPrice());
    }
    @Test
    void shouldSetCorrectName(){
        Asset currency = new Currency("Pound Sterling", "GBP", 4.90, 10, 0.20);
        currency.setName("British Pound Sterling");
        assertEquals("British Pound Sterling", currency.getName());
    }
    @Test
    void shouldSetCorrectSymbol(){
        Asset share = new Share("CD Project", "CDP", 100.0, 10);
        share.setSymbol("CDPR");;
        assertEquals("CDPR", share.getSymbol());
    }
    @Test
    void shouldSetCorrectCommodityFee(){
        Commodity commodity = new Commodity("CD Project", "CDP", 100.0, 10, 5);
        commodity.setCommodityFee(10);
        assertEquals(10, commodity.getCommodityFee());
    }
    @Test
    void shouldSetCorrectCurrencySpread(){
        Currency currency = new Currency("Pound Sterling", "GBP", 1.0, 10, 0.05);
        currency.setCurrencySpread(0.05);
        assertEquals(0.05, currency.getCurrencySpread());

    }
    @Test
    void shouldSetCorrectShareFee(){
        Share share = new Share("CD Project", "CDP", 100.0, 10);
        share.setFee(20);
        assertEquals(20, share.getFee());
    }
    @Test
    void shouldThrowExceptionWhenSetShareFeeToNegative(){
        Share share = new Share("CD Project", "CDP", 100.0, 10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            share.setFee(-1);
        });
        assertEquals("Opłata nie może być ujemna", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSetCommodityToNegative(){
        Commodity commodity = new Commodity("Gold", "GLD", 10.0, 10, 5);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commodity.setCommodityFee(-1);
        });
        assertEquals("Opłata nie może być ujemna", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSetSpreadIsLessThanZero(){
        Currency currency = new Currency("Pound Sterling", "GBP", 1.0, 10, 0.05);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            currency.setCurrencySpread(-1);
        });
        assertEquals("Spread nie może być ujemny", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenCommodityFeeIsLessThanZero(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Commodity commodity = new Commodity("Gold", "GLD", 10.0, 10, -1);
        });
        assertEquals("Opłata nie może być ujemna", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSpreadIsLessThanZero() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            Currency currency = new Currency("Pound Sterling", "GBP", 1.0, 10, -1);
        });
        assertEquals("Spread nie może być ujemny", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenCommodityFeeIsLessThanZero2ndConstructor(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Commodity commodity = new Commodity("Gold", "GLD", -1);
        });
        assertEquals("Opłata nie może być ujemna", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSpreadIsLessThanZero2ndConstructor() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            Currency currency = new Currency("Pound Sterling", "GBP",-1);
        });
        assertEquals("Spread nie może być ujemny", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenShareFeeIsLessThanZero2ndConstructor() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Share share = new Share("CD Project", "CDP", -1);
        });
        assertEquals("Opłata nie może być ujemna", exception.getMessage());
    }
}