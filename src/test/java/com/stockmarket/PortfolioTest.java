package com.stockmarket;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PortfolioTest {



    @Test
    void shouldInitializeWithCorrectCash(){
        Portfolio portfolio = new Portfolio(10000);
        assertEquals(10000, portfolio.getCash());
    }
    @Test
    void shouldInitializeWithEmptyHoldings(){
        Portfolio portfolio = new Portfolio(10000);
        assertEquals(0, portfolio.getHoldingsCount());
    }
    @Test
    void shouldThrowExceptionWhenInitialCashIsNegative(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Portfolio(-10000);
        });

        assertEquals("Gotówka musi być większa od zera", exception.getMessage());
    }
    @Test
    void shouldAddStockToEmptyPortfolio(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR =  new Stock("CD Project", "CDP", 100.0);
        portfolio.addStock(stockCDR, 10);

        assertEquals(1, portfolio.getHoldingsCount());
    }
    @Test
    void shouldAddStockToEmptyPortfolioWithMultipleHoldings(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        portfolio.addStock(stockCDR, 10);

        assertEquals(10, portfolio.getStockQuantity(stockCDR));
    }
    @Test
    void shouldNotIncreaseHoldingsCount(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        portfolio.addStock(stockCDR, 10);
        portfolio.addStock(stockCDR, 5);

        assertEquals(1, portfolio.getHoldingsCount());
    }
    @Test
    void shouldIncreaseQuantity(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        portfolio.addStock(stockCDR, 10);
        portfolio.addStock(stockCDR, 9);

        assertEquals(19, portfolio.getStockQuantity(stockCDR));
    }
    @Test
    void shouldThrowExceptionWhenStockIsNull(){
        Portfolio portfolio = new Portfolio(10000);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addStock(null, 10 );
        });

        assertEquals("Dodawana akcja nie może mieć wartości null",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenStockQuantityIsNegative(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addStock(stockCDR, -10);
        });

        assertEquals("Liczba dodawanych akcji nie może być mniejsza bądź równa 0", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenStockQuantityIsZero(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addStock(stockCDR, 0);
        });

        assertEquals("Liczba dodawanych akcji nie może być mniejsza bądź równa 0",  exception.getMessage());
    }
    @Test
    void shouldIncreaseStockQuantityWhenStocksSymbolsAreTheSame(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockCDR2 = new Stock("CD Project Red", "CDP", 100.0);
        portfolio.addStock(stockCDR, 10);
        portfolio.addStock(stockCDR2, 10);
        assertEquals(20, portfolio.getStockQuantity(stockCDR));
    }
    @Test
    void shouldIncreaseHoldingsCountForDifferentStocks(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockALE = new Stock("Allegro", "ALE", 80.0);
        portfolio.addStock(stockALE, 10);
        portfolio.addStock(stockCDR, 10);
        assertEquals(2, portfolio.getHoldingsCount());
    }
    @Test
    void shouldIncreaseStockQuantityForFirstStock(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockALE = new Stock("Allegro", "ALE", 80.0);
        portfolio.addStock(stockALE, 10);
        portfolio.addStock(stockCDR, 40);

        assertEquals(40, portfolio.getStockQuantity(stockCDR));
    }
    @Test
    void shouldIncreaseStockQuantityForSecondStock(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockALE = new Stock("Allegro", "ALE", 80.0);
        portfolio.addStock(stockALE, 10);
        portfolio.addStock(stockCDR, 40);

        assertEquals(10, portfolio.getStockQuantity(stockALE));
    }
    @Test
    void shouldCalculateStockValue(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        portfolio.addStock(stockCDR, 10);

        assertEquals(1000.0, portfolio.calculateStockValue());
    }
    @Test
    void shouldCalculateTotalValue(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        portfolio.addStock(stockCDR, 10);

        assertEquals(11000.0, portfolio.calculateTotalValue());
    }
    @Test
    void shouldCalculateStockValueForMultipleStocks(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockALE = new Stock("Allegro", "ALE", 80.0);
        portfolio.addStock(stockALE, 10);
        portfolio.addStock(stockCDR, 20);

        assertEquals(2800, portfolio.calculateStockValue());
    }
    @Test
    void shouldCalculateTotalValueForMultipleStocks(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockALE = new Stock("Allegro", "ALE", 80.0);
        portfolio.addStock(stockALE, 10);
        portfolio.addStock(stockCDR, 30);
        assertEquals(13800.0, portfolio.calculateTotalValue());
    }
    @Test
    void shouldCalculateZeroStockValueForEmptyPortfolio(){
        Portfolio portfolio = new Portfolio(0);

        assertEquals(0.0, portfolio.calculateStockValue());
    }
    @Test
    void shouldCalculateTotalValueForEmptyPortfolio(){
        Portfolio portfolio = new Portfolio(0);
        assertEquals(0.0, portfolio.calculateTotalValue());
    }
    @Test
    void shouldReturnZeroStockQuantity(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockALE = new Stock("Allegro", "ALE", 80.0);
        portfolio.addStock(stockCDR, 10);

        assertEquals(0, portfolio.getStockQuantity(stockALE));

    }
    @Test
    void shouldNotAddNewHolding(){
        Portfolio portfolio = new Portfolio(10000);
        Stock stockCDR = new Stock("CD Project", "CDP", 100.0);
        Stock stockALE = new Stock("Allegro", "ALE", 80.0);
        Stock stockMETA = new Stock("META", "META", 180.0);
        Stock stockRS = new Stock("Rock Star", "RS", 200.0);
        Stock stockINT = new Stock("Intel", "INT", 30.99);
        Stock stockNVD = new Stock("NVidia", "NVD", 300.50);
        Stock stockAMD = new Stock("AMD", "AMD", 199.10);
        Stock stockMS = new Stock("Microsoft", "MS", 299.55);
        Stock stockAPPL = new Stock("Apple", "APPLE", 469.94);
        Stock stockSONY = new Stock("Sony", "SONY", 249.85);
        Stock stockSMG = new Stock("Samsung", "SMG", 149.85);
        portfolio.addStock(stockALE, 1);
        portfolio.addStock(stockCDR, 3);
        portfolio.addStock(stockMETA, 10);
        portfolio.addStock(stockRS, 1);
        portfolio.addStock(stockAMD, 2);
        portfolio.addStock(stockMS, 10);
        portfolio.addStock(stockAPPL, 3);
        portfolio.addStock(stockSONY, 2);
        portfolio.addStock(stockINT, 5);
        portfolio.addStock(stockNVD, 3);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            portfolio.addStock(stockSMG, 1);
        });

        assertEquals("Nie można dodać więcej typów akcji", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenStockIsNullInGetStockQuantity(){
        Portfolio portfolio = new Portfolio(10000);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.getStockQuantity(null);
        });

        assertEquals("Akcja nie może mieć wartości null", exception.getMessage());
    }

}