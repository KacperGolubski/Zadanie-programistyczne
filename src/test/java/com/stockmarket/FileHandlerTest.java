package com.stockmarket;

import com.stockmarket.domain.*;
import com.stockmarket.logic.FileHandler;
import com.stockmarket.logic.Portfolio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private final String FILENAME = "portfolio.txt";
    public Portfolio portfolio;
    @BeforeEach
    void setUp(){
        portfolio = new Portfolio(10000);
    }

    @AfterEach
    void cleanUp() {
        File file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void shouldSaveAndLoadCashCorrectly() {
        Portfolio portfolio = new Portfolio(12345.67);
        FileHandler fileHandler = new FileHandler();
        fileHandler.savePortfolio(portfolio);
        Portfolio loadedPortfolio = new Portfolio(0);
        fileHandler.readPortfolio(loadedPortfolio);
        assertEquals(12345.67, loadedPortfolio.getCash(), 0.01);
    }

    @Test
    void shouldRestoredShareAssetTypeCorrectly() {
        Asset share = new Share("CD Project", "CDP", 100.0, 10);
        portfolio.buyAsset(share);
        FileHandler fileHandler = new FileHandler();
        fileHandler.savePortfolio(portfolio);
        Portfolio loadedPortfolio = new Portfolio(0);
        fileHandler.readPortfolio(loadedPortfolio);
        Map<String, Asset> holdings = loadedPortfolio.getHoldings();
        assertTrue(holdings.get("CDP") instanceof Share);
    }

    @Test
    void shouldRestoreCurrencySpreadCorrectly() {
        Asset currency = new Currency("Dollar", "USD", 4.0, 100, 0.05);
        portfolio.buyAsset(currency);
        FileHandler fileHandler = new FileHandler();
        fileHandler.savePortfolio(portfolio);
        Portfolio loadedPortfolio = new Portfolio(0);
        fileHandler.readPortfolio(loadedPortfolio);
        Currency loadedCurrency = (Currency) loadedPortfolio.getHoldings().get("USD");
        assertEquals(0.05, loadedCurrency.getCurrencySpread());
    }
    @Test
    void shouldRestoreCommodityFeeCorrectly() {
        Asset commodity = new Commodity("Gold", "GOLD", 9500, 1, 5);
        portfolio.buyAsset(commodity);
        FileHandler fileHandler = new FileHandler();
        fileHandler.savePortfolio(portfolio);
        Portfolio loadedPortfolio = new Portfolio(0);
        fileHandler.readPortfolio(loadedPortfolio);
        Commodity loadedCommodity = (Commodity) loadedPortfolio.getHoldings().get("GOLD");
        assertEquals(5,  loadedCommodity.getCommodityFee());
    }

    @Test
    void shouldRestoreQuantityCorrectlyFromLots() {
        Asset share = new Share("CD Project", "CDP", 100.0, 50); // 50 sztuk
        portfolio.buyAsset(share);
        FileHandler fileHandler = new FileHandler();
        fileHandler.savePortfolio(portfolio);
        Portfolio loadedPortfolio = new Portfolio(0);
        fileHandler.readPortfolio(loadedPortfolio);
        assertEquals(50.0, loadedPortfolio.getHoldings().get("CDP").getQuantity());
    }

    @Test
    void shouldThrowExceptionWhenFileQuantityDoesNotMatchLotsSum() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("CASH|1000.0");
            writer.newLine();
            writer.write("ASSET|SHARE|CDP|CD Project|100.0|5.0");
            writer.newLine();
            writer.write("LOT|2023-01-01T12:00:00|10.0|100.0");
            writer.newLine();
        }
        FileHandler fileHandler = new FileHandler();
        Portfolio portfolio = new Portfolio(0);
        assertThrows(IllegalStateException.class, () -> {
            fileHandler.readPortfolio(portfolio);
        });
    }

    @Test
    void shouldThrowExceptionForUnknownAssetType() throws IOException {
        // Given
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("CASH|1000.0");
            writer.newLine();
            writer.write("ASSET|CRYPTO|BTC|Bitcoin|1.0|0.0");
            writer.newLine();
        }
        FileHandler fileHandler = new FileHandler();
        Portfolio portfolio = new Portfolio(0);
        assertThrows(IllegalArgumentException.class, () -> {
            fileHandler.readPortfolio(portfolio);
        });
    }

    @Test
    void shouldHandleEmptyFileGracefully() {
        try {
            new File(FILENAME).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileHandler fileHandler = new FileHandler();
        Portfolio portfolio = new Portfolio(0);
        fileHandler.readPortfolio(portfolio);
        assertEquals(0, portfolio.getHoldings().size());
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() {
        FileHandler fileHandler = new FileHandler();
        Portfolio portfolio = new Portfolio(0);
        new File(FILENAME).delete();
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            fileHandler.readPortfolio(portfolio);
        });
        assertEquals("Błąd podczas odczytu portfela z pliku", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPathIsInvalid() {
        FileHandler fileHandler = new FileHandler();
        Portfolio portfolio = new Portfolio(1000.0);
        File directoryMock = new File(FILENAME);
        directoryMock.mkdir();
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            fileHandler.savePortfolio(portfolio);
        });
        assertEquals("Błąd podczas zapisu portfela do pliku", exception.getMessage());
        directoryMock.delete();
    }
}