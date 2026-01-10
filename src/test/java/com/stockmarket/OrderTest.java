package com.stockmarket;

import com.stockmarket.domain.*;
import com.stockmarket.logic.Order;
import com.stockmarket.logic.Portfolio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    @Test
    void shouldInitializeOrderWithCorrectSymbol() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        assertEquals("AAPL", order.getSymbol());
    }

    @Test
    void shouldGetName() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        assertEquals("Apple", order.getName());
    }

    @Test
    void shouldSetName() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        order.setName("Google");
        assertEquals("Google", order.getName());
    }

    @Test
    void shouldGetSymbol() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        assertEquals("AAPL", order.getSymbol());
    }

    @Test
    void shouldSetSymbol() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        order.setSymbol("GOOGL");
        assertEquals("GOOGL", order.getSymbol());
    }

    @Test
    void shouldGetTransactionType() {
        Order order = new Order("Apple", "AAPL", TransactionType.SELL, 10.0, 150.0);
        assertEquals(TransactionType.SELL, order.getTransactionType());
    }

    @Test
    void shouldSetTransactionType() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        order.setTransactionType(TransactionType.SELL);
        assertEquals(TransactionType.SELL, order.getTransactionType());
    }

    @Test
    void shouldGetOrderQuantity() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 55.5, 150.0);
        assertEquals(55.5, order.getOrderQuantity());
    }

    @Test
    void shouldSetOrderQuantity() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        order.setOrderQuantity(100.0);
        assertEquals(100.0, order.getOrderQuantity());
    }

    @Test
    void shouldGetPriceLimit() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.50);
        assertEquals(150.50, order.getPriceLimit());
    }

    @Test
    void shouldSetPriceLimit() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 150.0);
        order.setPriceLimit(200.0);
        assertEquals(200.0, order.getPriceLimit());
    }

    @Test
    void shouldReturnCorrectToStringFormat() {
        Order order = new Order("Apple", "AAPL", TransactionType.BUY, 10.0, 100.0);
        String expectedString = String.format("%-10s | %-15s | %-5s | Ilość: %8.2f | Wartość: %10.2f PLN", TransactionType.BUY, "Apple", "AAPL", 10.0, 100.0);
        assertEquals(expectedString, order.toString());
    }

    @Test
    void shouldPrioritizeHigherPriceForBuyOrders() {
        Order highPriceOrder = new Order("Higher", "H", TransactionType.BUY, 1, 200.0);
        Order lowPriceOrder = new Order("Lower", "L", TransactionType.BUY, 1, 100.0);
        assertEquals(-1, highPriceOrder.compareTo(lowPriceOrder));
    }

    @Test
    void shouldPrioritizeLowerPriceForSellOrders() {
        Order lowPriceOrder = new Order("Higher", "H", TransactionType.SELL, 1, 100.0);
        Order highPriceOrder = new Order("Lower", "L", TransactionType.SELL, 1, 200.0);
        assertEquals(-1, lowPriceOrder.compareTo(highPriceOrder));
    }


}

