package com.company.models;

import java.math.BigDecimal;

public class Asset {

    public int id;
    public String symbol;
    public BigDecimal price;
    public Integer categoryId;

    public Asset() {}

    public Asset(int id, String symbol, BigDecimal price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }

    public Asset(int id, String symbol, BigDecimal price, Integer categoryId) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Asset(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " == " + symbol + " == " + price + " cat=" + categoryId;
    }
}
