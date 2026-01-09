package com.stockmarket.logic;

import com.stockmarket.domain.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FileHandler {
    // Zmienne
    private Asset loadedAsset = null;
    private double quantityValidatorAsset;
    // Konstruktor
    public void savePortfolio(Portfolio portfolio) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("portfolio.txt"))) {
            writer.write("CASH|" + portfolio.getCash());
            writer.newLine();
            for(Map.Entry<String, Asset> entry : portfolio.getHoldings().entrySet()){
                String symbol = entry.getKey();
                Asset asset = entry.getValue();
                String name = asset.getName();
                writer.write("ASSET|" + asset.getAssetType() + "|" + symbol + "|" + name + "|" + asset.getQuantity());

                if (asset.getAssetType() == AssetType.SHARE) {
                    Share share = (Share) asset;
                    writer.write("|" + share.getFee());
                } else if (asset.getAssetType() == AssetType.CURRENCY) {
                    Currency currency = (Currency) asset;
                    writer.write("|" + currency.getCurrencySpread());
                } else if (asset.getAssetType() == AssetType.COMMODITY) {
                    Commodity commodity = (Commodity) asset;
                    writer.write("|" + commodity.getCommodityFee());
                }
                writer.newLine();

                for(PurchaseLot purchaseLot : asset.getPurchaseLots()){
                    writer.write("LOT|" + purchaseLot.getDate() + "|" + purchaseLot.getQuantity() + "|" + purchaseLot.getInitialPrice());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Błąd podczas zapisu portfela do pliku");
        }
    }
    // Metody
    public void readPortfolio(Portfolio portfolio) {
        try(BufferedReader reader = new BufferedReader(new FileReader("portfolio.txt"))) {
            String line;
            while ((line = reader.readLine()) != null){
                parseLine(line, portfolio);
            }
            validateQuantity();
        }
        catch (IOException e) {
            throw new IllegalStateException("Błąd podczas odczytu portfela z pliku");
        }
    }

    public void parseLine(String line, Portfolio portfolio) {
        String[] parts = line.split("\\|");
        switch(parts[0]){
            case "CASH":
                double cash = Double.parseDouble(parts[1]);
                portfolio.setCash(cash);
                break;
            case "ASSET":
                validateQuantity();
                AssetType assetType = AssetType.valueOf(parts[1]);
                String symbol = parts[2];
                String name = parts[3];
                quantityValidatorAsset = Double.parseDouble(parts[4]);
                double fee = Double.parseDouble(parts[5]);
                switch (assetType){
                    case SHARE:
                        loadedAsset = new Share(name, symbol, fee);
                        break;
                    case CURRENCY:
                        loadedAsset = new Currency(name, symbol, fee);
                        break;
                    case COMMODITY:
                        loadedAsset = new Commodity(name, symbol, fee);
                        break;
                    default:
                        throw new IllegalArgumentException("Nieznany typ aktywa: " + assetType);
                }
                portfolio.getHoldings().put(symbol, loadedAsset);
                break;
            case "LOT":
                LocalDateTime date = LocalDateTime.parse(parts[1]);
                double quantity = Double.parseDouble(parts[2]);
                double price = Double.parseDouble(parts[3]);
                PurchaseLot lot = new PurchaseLot(price, quantity, date);
                if (loadedAsset != null) {
                    loadedAsset.addPurchaseLot(lot);
                }
                break;
            default:
                throw new IllegalArgumentException("Nieznana linia: " + line);
        }
    }

    private void validateQuantity(){
        if(loadedAsset != null){
            double quantityValidatorLots = loadedAsset.getQuantity();
            if(Math.abs(quantityValidatorAsset - quantityValidatorLots) > 0.0001){
                throw new IllegalStateException("Liczba zapisanych aktywów nie jest równa wczytanym aktywom");
            }
        }
    }

}
