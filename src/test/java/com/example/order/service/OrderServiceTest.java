package com.example.order.service;

import com.example.kafka.TradeProducer;
import com.example.model.Order;
import com.example.model.OrderType;
import com.example.model.Trade;
import com.example.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;
    private TradeProducer tradeProducer;

    @BeforeEach
    void setup() {
        tradeProducer = Mockito.mock(TradeProducer.class);
        orderService = new OrderService(tradeProducer);
    }

    @Test
    void testFullMatch() {
        Order buy = new Order("buy001", OrderType.BUY, 100.0, 10);
        Order sell = new Order("sell001", OrderType.SELL, 100.0, 10);

        orderService.processOrder(sell);  // seller goes first
        orderService.processOrder(buy);   // buyer matches

        List<Trade> trades = orderService.getExecutedTrades();
        assertEquals(1, trades.size());

        Trade trade = trades.get(0);
        assertEquals(10, trade.getQuantity());
        assertEquals(100.0, trade.getPrice());

        Mockito.verify(tradeProducer).sendTrade(trade);
    }

    @Test
    void testPartialMatch() {
        // Buyer wants 50 @ $10.00
        Order buy = new Order("buy002", OrderType.BUY, 10.0, 50);

        // Seller offers 100 @ $10.00
        Order sell = new Order("sell002", OrderType.SELL, 10.0, 100);

        orderService.processOrder(sell);

        // Then buyer places order that partially matches seller
        orderService.processOrder(buy);

        // Only 50 units matched, so one trade expected
        List<Trade> trades = orderService.getExecutedTrades();
        assertEquals(1, trades.size());

        Trade trade = trades.get(0);
        assertEquals(50, trade.getQuantity());
        assertEquals(10.0, trade.getPrice());

        // Seller still has 50 left
        List<Order> remainingSells = orderService.getOpenSellOrders();
        assertEquals(1, remainingSells.size());
        assertEquals(50, remainingSells.get(0).getQuantity());
    }


    @Test
    void testNoMatchDueToPrice() {
        Order buy = new Order("buy003", OrderType.BUY, 100.0, 8);
        Order sell = new Order("sell003", OrderType.SELL, 110.0, 10);

        orderService.processOrder(sell);
        orderService.processOrder(buy);

        assertTrue(orderService.getExecutedTrades().isEmpty());
        assertEquals(1, orderService.getOpenBuyOrders().size());
        assertEquals(1, orderService.getOpenSellOrders().size());
    }
}
