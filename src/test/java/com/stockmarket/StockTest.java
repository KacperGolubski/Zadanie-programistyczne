package com.stockmarket;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
    @Test
    void TestNameGetter(){
        Stock testStock = new Stock("CD Projekt", "CDP", 100.00);

        assertEquals("CD Projekt", testStock.getName(), "Nazwa akcji nie zgadza się z oczekiwaną wartością");
    }
    @Test
    void TestSymbolGetter(){
        Stock testStock = new Stock("CD Projekt", "CDP", 100.00);

        assertEquals("CDP", testStock.getSymbol(), "Symbol akcji nie zgadza się z oczekiwaną wartością");
    }
    @Test
    void TestPriceGetter(){
        Stock testStock = new Stock("CD Projekt", "CDP", 100.00);

        assertEquals(100, testStock.getInitialPrice(), "Cena akcji nie zgadza się z oczekiwaną wartością");
    }
    @Test
    void shouldThrowExceptionWhenInitialPriceIsZero() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Testowa Firma", "TEST", 0.0);
        });

        assertEquals("Cena akcji musi być liczbą dodatnią", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenInitialPriceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Testowa Firma", "TEST", -10.0);
        });

        assertEquals("Cena akcji musi być liczbą dodatnią",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenInitialPriceIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Testowa Firma", "TEST", null);
        });

        assertEquals("Cena akcji musi być liczbą dodatnią", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock(" ", "TEST", 100.0);
        });

        assertEquals("Nazwa akcji nie może być pusta albo null",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock(null, "TEST", 100.0);
        });

        assertEquals("Nazwa akcji nie może być pusta albo null",   exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Test", " ", 100.0);
        });

        assertEquals("Symbol akcji nie może być pusty albo null",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Test", null, 100.0);
        });

        assertEquals("Symbol akcji nie może być pusty albo null",    exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameHasNoLetters() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("@@@@", "CDP", 100.0);
        });

        assertEquals("Nazwa akcji musi zawierać przynajmniej jedną literę",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolHasNoLetters() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("CDP", "1234", 100.0);
        });

        assertEquals("Symbol akcji musi zawierać przynajmniej jedną literę",   exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenSymbolLengthIsTooLong() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("Test", "1234p6", 100.0);
        });

        assertEquals("Symbol nie może być dłuższy niż 5 znaków", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameLengthIsTooShort() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("P", "test", 100.0);
        });

        assertEquals("Nazwa musi mieć conajmniej 2 znaki i nie może dłuższa niż 30 znaków", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenNameLengthIsTooLong() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Stock("T12345678901234567890123456789T", "test", 100.0);
        });

        assertEquals("Nazwa musi mieć conajmniej 2 znaki i nie może dłuższa niż 30 znaków", exception.getMessage());
    }
    @Test
    void shouldAllowNameWithExactly30Characters() {
        assertDoesNotThrow(() -> {
            new Stock("ToJestBardzoDługaNazwaFirmy30", "test", 100.0);
        });
    }
    @Test
    void shouldAllowSymbolWithExactly5Characters() {
        assertDoesNotThrow(() -> {
            new Stock("Test", "Test0", 100.0);
        });
    }
    @Test
    void shouldAllowPositiveInitialPrice(){
        assertDoesNotThrow(() -> {
            new Stock("Test", "Test", 0.01);
        });
    }
    @Test
    void shouldBeEqualWhenSymbolsAreEqual(){
        Stock stock1 = new Stock("CD Project", "CDP", 100.0);
        Stock stock2 = new Stock("CD Project Red", "CDP", 100.0);

        assertTrue(stock1.equals(stock2));
    }
    @Test
    void shouldNotBeEqualWhenSymbolsAreEqual(){
        Stock stock1 = new Stock("CD Project", "CDP", 100.0);
        Stock stock2 = new Stock("CD Project Red", "CDP2", 100.0);

        assertFalse(stock1.equals(stock2));
    }
    @Test
    void shouldBeEqualWhenSymbolsAreEqualHashCode(){
        Stock stock1 = new Stock("CD Project", "CDP", 100.0);
        Stock stock2 = new Stock("CD Project Red", "CDP", 100.0);
        assertEquals(stock1.hashCode(), stock2.hashCode());
    }
    @Test
    void shouldNotBeEqualWhenSymbolIsNull() {
        Stock stock1 = new Stock("CD Project", "CDP", 100.0);
        assertFalse(stock1.equals(null));
    }





}
