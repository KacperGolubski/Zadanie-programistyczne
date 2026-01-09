package com.stockmarket.logic;

import com.stockmarket.domain.Asset;

import java.util.Comparator;

public class AssetComparator implements Comparator<Asset> {
    @Override
    public int compare(Asset o1, Asset o2) {
        int type = o1.getAssetType().compareTo(o2.getAssetType());
        if(type != 0){
            return type;
        }
        return Double.compare(o2.calculateMarketValue(), o1.calculateMarketValue());
    }
}
