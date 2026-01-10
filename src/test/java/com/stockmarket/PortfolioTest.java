package com.stockmarket;

import com.stockmarket.domain.*;
import com.stockmarket.logic.Order;
import com.stockmarket.logic.Portfolio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        eur = new Currency("Euro", "EUR", 4.20, 200, 0.20);
        gbp = new Currency("Pound Sterling", "GBP", 4.80, 300, 0.333);
        usd = new Currency("American Dollar", "USD", 3.60, 400, 0.11);
    }

    @Test
    void shouldInitializeWithCorrectCash() {
        assertEquals(100000, portfolio.getCash());
    }

    @Test
    void shouldInitializeWithEmptyHoldings() {
        assertEquals(0, portfolio.getHoldings().size());
    }

    @Test
    void shouldThrowExceptionWhenInitialCashIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Portfolio(-10000);
        });

        assertEquals("Gotówka musi być większa od zera", exception.getMessage());
    }

    @Test
    void shouldAddShareToEmptyPortfolio() {
        portfolio.buyAsset(eur);
        assertEquals(1, portfolio.getHoldings().size());
    }

    @Test
    void shouldAddStockToEmptyPortfolioWithMultipleHoldings() {
        portfolio.buyAsset(oil);
        assertEquals(20, portfolio.getHoldingQuantity(oil));
    }

    @Test
    void shouldNotIncreaseHoldingsCount() {
        portfolio.buyAsset(gold);
        portfolio.buyAsset(gold);
        assertEquals(1, portfolio.getHoldings().size());
    }

    @Test
    void shouldIncreaseQuantity() {
        portfolio.buyAsset(usd);
        portfolio.buyAsset(usd);
        assertEquals(800, portfolio.getHoldingQuantity(usd));
    }

    @Test
    void shouldThrowExceptionWhenAssetIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.buyAsset(null);
        });

        assertEquals("Dodawane aktywo nie może mieć wartości null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPortfolioIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.buyAsset(null);
        });

        assertEquals("Dodawane aktywo nie może mieć wartości null", exception.getMessage());
    }

    @Test
    void shouldIncreaseStockQuantityWhenStocksSymbolsAreTheSame() {
        Asset shareCDR2 = new Share("CD Project Red", "CDP", 100.0, 15);
        portfolio.buyAsset(shareCDR);
        portfolio.buyAsset(shareCDR2);
        assertEquals(25, portfolio.getHoldingQuantity(shareCDR));
    }

    @Test
    void shouldIncreaseHoldingsCountForDifferentStocks() {

        portfolio.buyAsset(shareALE);
        portfolio.buyAsset(shareCDR);
        assertEquals(2, portfolio.getHoldings().size());
    }

    @Test
    void shouldIncreaseStockQuantityForFirstStock() {
        portfolio.buyAsset(shareASC);
        portfolio.buyAsset(shareCDR);

        assertEquals(20, portfolio.getHoldingQuantity(shareASC));
    }

    @Test
    void shouldIncreaseStockQuantityForSecondStock() {
        portfolio.buyAsset(shareALE);
        portfolio.buyAsset(shareCMR);

        assertEquals(1, portfolio.getHoldingQuantity(shareCMR));
    }

    @Test
    void shouldReturnCorrectCashValue() {
        Portfolio portfolio = new Portfolio(4000);

        Asset share = new Share("CD Project", "CDP", 100.0, 10);
        Asset commodity = new Commodity("Gold", "GLD", 100.0, 10, 5);
        Asset currency = new Currency("Euro", "EUR", 100.0, 10, 5);
        share.setCurrentPrice(100.0);
        commodity.setCurrentPrice(100.0);
        currency.setCurrentPrice(100.0);
        portfolio.buyAsset(commodity);
        portfolio.buyAsset(currency);
        portfolio.buyAsset(share);

        assertEquals(1000, portfolio.getCash());
    }

    @Test
    void shouldCalculateTotalValueForEmptyPortfolio() {
        Portfolio emptyPortfolio = new Portfolio(0);
        assertEquals(0.0, emptyPortfolio.calculatePortfolioValue());
    }

    @Test
    void shouldReturnZeroStockQuantity() {
        portfolio.buyAsset(shareCDR);
        assertEquals(0, portfolio.getHoldingQuantity(shareALE));
    }

    @Test
    void StockHoldingGetter() {
        portfolio.buyAsset(usd);
        assertNotNull(portfolio.getHoldings(), "Tablica aktyw nie powinna być null.");
    }

    @Test
    void shouldThrowExceptionWhenStockIsNullInGetStockQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.getHoldingQuantity(null);
        });

        assertEquals("Aktywo nie może mieć wartości null", exception.getMessage());
    }

    @Test
    void shouldReplaceHoldingsWithNewList() {
        Portfolio testportfolio = new Portfolio(10000);

        Map<String, Asset> newHoldings = new HashMap<>();
        newHoldings.put("OIL", oil);
        newHoldings.put("GOLD", gold);

        testportfolio.setHoldings(newHoldings);

        assertEquals(2, testportfolio.getHoldings().size());
    }

    @Test
    void shouldThrowExceptionWhenHoldingListIsNULL() {
        Portfolio portfolio = new Portfolio(10000);
        portfolio.setHoldings(null);

        assertNull(portfolio.getHoldings());
    }

    @Test
    void shouldReturnCorrectQuantity() {
        portfolio.buyAsset(gold);
        portfolio.getHoldingQuantity(gold);
        assertEquals(1.5, portfolio.getHoldingQuantity(gold));
    }

    @Test
    void shouldThrowExceptionWhenCashIsSmallerThanNewAsset() {
        Portfolio portfolio = new Portfolio(1);
        gold.setCurrentPrice(100.0);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            portfolio.buyAsset(gold);
            ;
        });

        assertEquals("Za mało pieniędzy w portfelu", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHoldingIsNull() {
        portfolio.setHoldings(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.buyAsset(gold);
        });
        assertEquals("Lista aktywów nie została utworzona", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAssetsWithSameSymbolHasDifferentTypes() {
        Asset commodityGold = new Commodity("Gold", "GOLD", 12500, 1.5, 5);
        Asset shareGold = new Share("Gold Bullion", "GOLD", 1200.0, 5);

        portfolio.buyAsset(shareGold);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.buyAsset(commodityGold);
        });
        assertEquals("Portfel zawiera już aktywa z tym samym symbolem", exception.getMessage());
    }

    @Test
    void shouldTreatAssetsWithDifferentSymbolsAsDifferentTypes() {
        Asset commodityGold = new Commodity("Gold", "GOLD", 12500, 1.5, 5);
        Asset shareGold = new Share("Gold Bullion", "GOLD", 1200.0, 5);
        assertFalse(commodityGold.equals(shareGold));
    }

    @Test
    void shouldCalculateHoldingsValue() {
        shareCDR.setCurrentPrice(100.0);
        gold.setCurrentPrice(12500.0);
        gbp.setCurrentPrice(4.80);
        portfolio.buyAsset(shareCDR);
        portfolio.buyAsset(gold);
        portfolio.buyAsset(gbp);

        assertEquals(21077.6, portfolio.calculateHoldingsValue());
    }

    @Test
    void shouldCalculatePortfolioValue() {
        Portfolio portfolio = new Portfolio(30000);
        portfolio.buyAsset(shareCDR);
        shareCDR.setCurrentPrice(100.0);
        portfolio.buyAsset(gold);
        gold.setCurrentPrice(12500.0);
        portfolio.buyAsset(gbp);
        gbp.setCurrentPrice(4.80);

        assertEquals(29887.6, portfolio.calculatePortfolioValue());
    }

    @Test
    void shouldSellAsset() {
        Portfolio portfolio = new Portfolio(10000);
        Asset shareCDR1 = new Share("CD Project", "CDP", 100.0, 10);
        Asset shareCDR2 = new Share("CD Project", "CDP", 200.0, 10);
        portfolio.buyAsset(shareCDR1);
        portfolio.buyAsset(shareCDR2);
        shareCDR1.setCurrentPrice(150.0);
        portfolio.sellAsset("CDP", 15);
        assertEquals(9245.0, portfolio.getCash(), 0.01);
    }

    @Test
    void shouldNotSellMoreAssetThanOwned() {
        Asset shareCDR1 = new Share("CD Project", "CDP", 100.0, 1);
        portfolio.buyAsset(shareCDR1);
        shareCDR1.setCurrentPrice(150.0);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            portfolio.sellAsset("CDP", 10);
        });
        assertEquals("Nie można sprzedać więcej aktywów niż posiadasz", exception.getMessage());
    }

    @Test
    void shouldSellOldestAssetFirst() {
        Asset shareOld = new Share("CD Project", "CDP", 100.0, 1);
        Asset shareNew = new Share("CD Project", "CDP", 300.0, 1);
        portfolio.buyAsset(shareOld);
        portfolio.buyAsset(shareNew);
        shareOld.setCurrentPrice(150.0);
        assertEquals("Aktywa sprzedano z zyskiem: 45.0", portfolio.sellAsset("CDP", 1));
    }

    @Test
    void shouldSellSecondAssetWithDifferentType() {
        Asset share = new Share("CD Project", "CDP", 100.0, 1);
        Asset currency = new Currency("Euro", "EUR", 5.00, 20, 0.20);
        portfolio.buyAsset(share);
        portfolio.buyAsset(currency);
        share.setCurrentPrice(120.0);
        currency.setCurrentPrice(4.80);
        portfolio.sellAsset("EUR", 20);
        assertEquals(0, portfolio.getHoldingQuantity(currency));

    }

    @Test
    void shouldThrowIllegalStateExceptionWhenAssetCurrentPriceIsNotSet() {
        Asset currency = new Currency("Euro", "EUR", 5.00, 20, 0.20);
        portfolio.buyAsset(currency);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            portfolio.sellAsset("EUR", 20);
        });
        assertEquals("Cena aktywa nie została ustawiona", exception.getMessage());
    }

    @Test
    void shouldReturnReportWithAssets() {
        Asset share = new Share("CD Project", "CDP", 100.0, 10);
        share.setCurrentPrice(100.0);
        portfolio.buyAsset(share);
        String expectedString = "SHARE      | CD Project      | CDP   | Ilość:    10,00 | Wartość:    1000,00 PLN";
        List<String> report = portfolio.printReport();
        assertEquals(expectedString, report.get(0));
    }

    @Test
    void shouldReturnOrdersSortedByPriority() {
        Order lowPriorityOrder = new Order("Tanie", "LOW", TransactionType.BUY, 1, 100.0);
        Order highPriorityOrder = new Order("Drogie", "HIGH", TransactionType.BUY, 1, 200.0);

        portfolio.addOrder(lowPriorityOrder);
        portfolio.addOrder(highPriorityOrder);
        String expectedFirstLine = String.format("%-10s | %-15s | %-5s | Ilość: %8.2f | Wartość: %10.2f PLN", TransactionType.BUY, "Drogie", "HIGH", 1.0, 200.0);
        List<String> orders = portfolio.printOrder();
        assertEquals(expectedFirstLine, orders.get(0));
    }

    @Test
    void shouldReturnWatchListContent() {
        Asset share = new Share("Tesla", "TSLA", 200.0, 1);
        portfolio.addToWatchList(share);
        String expectedExactString = share.toString();
        List<String> watchList = portfolio.printWatchList();
        assertEquals(expectedExactString, watchList.get(0));

    }

    @Test
    void shouldThrowExceptionWhenWatchListIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.printWatchList();
        });
        assertEquals("Lista jest pusta", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAssetInWatchListIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            portfolio.addToWatchList(null);
        });
        assertEquals("Asset nie może być null",  exception.getMessage());
    }
}



