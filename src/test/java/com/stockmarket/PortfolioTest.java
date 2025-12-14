package com.stockmarket;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.Commodity;
import com.stockmarket.domain.Currency;
import com.stockmarket.domain.Share;
import com.stockmarket.logic.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioTest {

    private Portfolio portfolio;
    private Asset shareCDR;
    private Asset shareALE;
    private Asset shareASC;
    private Asset shareCMR;
    private Asset gold;
    private Asset oil;
    private Asset eur;
    private Asset gbp;
    private Asset usd;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio(100000);
        shareCDR = new Share("CD Project", "CDP", 100.0, 10);
        shareALE = new Share("Allegro Project", "ALE", 80.0, 15);
        shareASC = new Share("Asseco", "ASC", 50.0, 20);
        shareCMR = new Share("Comarch", "CMR", 60, 1);
        gold = new Commodity("Gold", "GOLD", 12500, 1.5, 5);
        oil = new Commodity("Oil", "OIL", 216, 20, 4);
        eur = new Currency("Euro", "EUR", 4.20, 200,0.20);
        gbp = new Currency("Pound Sterling", "GBP", 4.80, 300, 0.333);
        usd = new Currency("American Dollar", "USD", 3.60, 400, 0.11);
    }


    @Test
    void shouldInitializeWithCorrectCash(){
        assertEquals(100000, portfolio.getCash());
    }
    @Test
    void shouldInitializeWithEmptyHoldings(){
        assertEquals(0, portfolio.getHoldings().size());
    }
    @Test
    void shouldThrowExceptionWhenInitialCashIsNegative(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Portfolio(-10000);
        });

        assertEquals("Gotówka musi być większa od zera", exception.getMessage());
    }
    @Test
    void shouldAddShareToEmptyPortfolio(){
        portfolio.addAsset(eur);
        assertEquals(1, portfolio.getHoldings().size());
    }
    @Test
    void shouldAddStockToEmptyPortfolioWithMultipleHoldings(){
        portfolio.addAsset(oil);
        assertEquals(20, portfolio.getHoldingQuantity(oil));
    }
    @Test
    void shouldNotIncreaseHoldingsCount(){
        portfolio.addAsset(gold);
        portfolio.addAsset(gold);
        assertEquals(1, portfolio.getHoldings().size());
    }
    @Test
    void shouldIncreaseQuantity(){
        portfolio.addAsset(usd);
        portfolio.addAsset(usd);
        assertEquals(800, portfolio.getHoldingQuantity(usd));
    }
    @Test
    void shouldThrowExceptionWhenAssetIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addAsset(null);
        });

        assertEquals("Dodawane aktywo nie może mieć wartości null",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenPortfolioIsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addAsset(null);
        });

        assertEquals("Dodawane aktywo nie może mieć wartości null",  exception.getMessage());
    }
    @Test
    void shouldIncreaseStockQuantityWhenStocksSymbolsAreTheSame(){
        Asset shareCDR2 = new Share("CD Project Red", "CDP", 100.0, 15);
        portfolio.addAsset(shareCDR);
        portfolio.addAsset(shareCDR2);
        assertEquals(25, portfolio.getHoldingQuantity(shareCDR));
    }
    @Test
    void shouldIncreaseHoldingsCountForDifferentStocks(){

        portfolio.addAsset(shareALE);
        portfolio.addAsset(shareCDR);
        assertEquals(2, portfolio.getHoldings().size());
    }
    @Test
    void shouldIncreaseStockQuantityForFirstStock(){
        portfolio.addAsset(shareASC);
        portfolio.addAsset(shareCDR);

        assertEquals(20, portfolio.getHoldingQuantity(shareASC));
    }
    @Test
    void shouldIncreaseStockQuantityForSecondStock(){
        portfolio.addAsset(shareALE);
        portfolio.addAsset(shareCMR);

        assertEquals(1, portfolio.getHoldingQuantity(shareCMR));
    }
    @Test
    void shouldReturnCorrectCashValue(){
        Portfolio portfolio = new Portfolio(4000);

        Asset share = new Share("CD Project", "CDP", 100.0, 10);
        Asset commodity = new Commodity("Gold", "GLD", 100.0, 10, 5);
        Asset currency = new Currency("Euro", "EUR", 100.0, 10, 5);

        portfolio.addAsset(commodity);
        portfolio.addAsset(currency);
        portfolio.addAsset(share);

        assertEquals(1000, portfolio.getCash());
    }
    @Test
    void shouldCalculateTotalValueForEmptyPortfolio(){
        Portfolio emptyPortfolio = new Portfolio(0);
        assertEquals(0.0, emptyPortfolio.calculatePortfolioValue());
    }
    @Test
    void shouldReturnZeroStockQuantity(){
        portfolio.addAsset(shareCDR);
        assertEquals(0, portfolio.getHoldingQuantity(shareALE));
    }
    @Test
    void StockHoldingGetter(){
        portfolio.addAsset(usd);
        assertNotNull(portfolio.getHoldings(), "Tablica aktyw nie powinna być null.");
    }
    @Test
    void shouldThrowExceptionWhenStockIsNullInGetStockQuantity(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.getHoldingQuantity(null);
        });

        assertEquals("Aktywo nie może mieć wartości null", exception.getMessage());
    }
    @Test
    void shouldReplaceHoldingsWithNewList() {
        Portfolio testportfolio = new Portfolio(10000);

        List<Asset> newHoldings = new ArrayList<>();
        newHoldings.add(eur);
        newHoldings.add(oil);

        testportfolio.setHoldings(newHoldings);

        assertEquals(2, testportfolio.getHoldings().size());
    }
    @Test
    void shouldThrowExceptionWhenHoldingListIsNULL(){
        Portfolio portfolio = new Portfolio(10000);
        portfolio.setHoldings(null);

        assertNull(portfolio.getHoldings());
    }
    @Test
    void shouldReturnCorrectQuantity(){
        portfolio.addAsset(gold);
        portfolio.getHoldingQuantity(gold);
        assertEquals(1.5, portfolio.getHoldingQuantity(gold));
    }
    @Test
    void shouldThrowExceptionWhenCashIsSmallerThanNewAsset(){
        Portfolio portfolio = new Portfolio(1);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            portfolio.addAsset(gold);;
        });

        assertEquals("Za mało pieniędzy w portfelu", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenQuantityIsNegative(){
        shareCDR.setQuantity(-1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addAsset(shareCDR);
        });
        assertEquals("Liczba dodawanych aktywów nie może być mniejsza bądź równa 0",  exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenHoldingIsNull(){
        portfolio.setHoldings(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addAsset(gold);
        });
        assertEquals("Lista aktywów nie została utworzona", exception.getMessage());
    }
    @Test
    void shouldTreatAssetsWithSameSymbolAsDifferentTypes() {
    Asset commodityGold = new Commodity("Gold", "GOLD", 12500, 1.5, 5);
    Asset shareGold = new Share("Gold Bullion", "GOLD", 1200.0, 5);

    portfolio.addAsset(shareGold);
    portfolio.addAsset(commodityGold);

    assertEquals(2, portfolio.getHoldings().size());
    }
    @Test
    void shouldTreatAssetsWithDifferentSymbolsAsDifferentTypes() {
        Asset commodityGold = new Commodity("Gold", "GOLD", 12500, 1.5, 5);
        Asset shareGold = new Share("Gold Bullion", "GOLD", 1200.0, 5);
        assertFalse(commodityGold.equals(shareGold));
    }
    @Test
    void shouldCalculateHoldingsValue(){
        portfolio.addAsset(shareCDR);
        portfolio.addAsset(gold);
        portfolio.addAsset(gbp);

        assertEquals(21077.6, portfolio.calculateHoldingsValue());
    }
    @Test
    void shouldCalculateTotalValue(){
        Portfolio portfolio = new Portfolio (30000);
        portfolio.addAsset(shareCDR);
        portfolio.addAsset(gold);
        portfolio.addAsset(gbp);

        assertEquals(29887.6, portfolio.calculatePortfolioValue());
    }

}