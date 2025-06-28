package com.example.order.service;

import com.example.kafka.TradeProducer;
import com.example.model.Order;
import com.example.model.OrderType;
import com.example.model.Trade;
import com.example.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OrderSimulationTest {

    @Test
    void simulateBulkOrders() {
        TradeProducer mockProducer = Mockito.mock(TradeProducer.class);
        OrderService orderService = new OrderService(mockProducer);
        Random random = new Random();

        int totalOrders = 1000;

        for (int i = 0; i < totalOrders; i++) {
            OrderType type = random.nextBoolean() ? OrderType.BUY : OrderType.SELL;
            String id = type == OrderType.BUY ? "buy" + i : "sell" + i;
            int quantity = random.nextInt(50) + 1; // 1–50 shares
            double price = 10 + (random.nextDouble() * 10); // $10–$20

            Order order = new Order(id, type, Math.round(price * 100.0) / 100.0, quantity);
            order.setTimestamp(LocalDateTime.now());
            orderService.processOrder(order);
        }

        List<Trade> trades = orderService.getExecutedTrades();
        System.out.println("Total executed trades: " + trades.size());
        System.out.println("Remaining BUY orders: " + orderService.getOpenBuyOrders().size());
        System.out.println("Remaining SELL orders: " + orderService.getOpenSellOrders().size());
    }
}
