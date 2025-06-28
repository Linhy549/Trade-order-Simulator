package com.example.service;

import com.example.kafka.TradeProducer;
import com.example.model.Order;
import com.example.model.OrderType;
import com.example.model.Trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

@Service
public class OrderService {
    private final PriorityQueue<Order> buyOrders;
    private final PriorityQueue<Order> sellOrders;
    private final List<Trade> executedTrades;
    private final TradeProducer tradeProducer;

    @Autowired
    public OrderService(TradeProducer tradeProducer) {
        buyOrders = new PriorityQueue<>(
                (o1, o2) -> {
                    int priceCmp = Double.compare(o2.getPrice(), o1.getPrice());
                    return priceCmp != 0 ? priceCmp : o1.getTimestamp().compareTo(o2.getTimestamp());
                });

        sellOrders = new PriorityQueue<>(
                (o1, o2) -> {
                    int priceCmp = Double.compare(o1.getPrice(), o2.getPrice());
                    return priceCmp != 0 ? priceCmp : o1.getTimestamp().compareTo(o2.getTimestamp());
                });

        executedTrades = new ArrayList<Trade>();
		this.tradeProducer = tradeProducer;
    }

    public void processOrder(Order order) {
        if (order.getType() == OrderType.BUY) {
            matchBuyOrder(order);
        } else {
            matchSellOrder(order);
        }
    }

    private void matchBuyOrder(Order buyOrder) {
        while (!sellOrders.isEmpty() && buyOrder.getQuantity() > 0 &&
                sellOrders.peek().getPrice() <= buyOrder.getPrice()) {

            Order sellOrder = sellOrders.poll();
            int tradeQty = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

            Trade trade = new Trade(UUID.randomUUID().toString(),
                    buyOrder.getOrderId(),
                    sellOrder.getOrderId(),
                    sellOrder.getPrice(),
                    tradeQty,
                    LocalDateTime.now());
            executedTrades.add(trade);
            tradeProducer.sendTrade(trade); 
            System.out.println("Sent trade to Kafka: " + trade.getTradeId());

            buyOrder.setQuantity(buyOrder.getQuantity() - tradeQty);
            sellOrder.setQuantity(sellOrder.getQuantity() - tradeQty);

            if (sellOrder.getQuantity() > 0) {
                sellOrders.add(sellOrder);
            }
        }

        if (buyOrder.getQuantity() > 0) {
            buyOrders.add(buyOrder);
        }
    }

    private void matchSellOrder(Order sellOrder) {
        while (!buyOrders.isEmpty() && sellOrder.getQuantity() > 0 &&
                buyOrders.peek().getPrice() >= sellOrder.getPrice()) {

            Order buyOrder = buyOrders.poll();
            int tradeQty = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

            Trade trade = new Trade(UUID.randomUUID().toString(),
                    buyOrder.getOrderId(),
                    sellOrder.getOrderId(),
                    sellOrder.getPrice(),
                    tradeQty,
                    LocalDateTime.now());
            executedTrades.add(trade);
            tradeProducer.sendTrade(trade); 
            System.out.println("Sent trade to Kafka: " + trade.getTradeId());
            buyOrder.setQuantity(buyOrder.getQuantity() - tradeQty);
            sellOrder.setQuantity(sellOrder.getQuantity() - tradeQty);

            if (buyOrder.getQuantity() > 0) {
                buyOrders.add(buyOrder);
            }
        }

        if (sellOrder.getQuantity() > 0) {
            sellOrders.add(sellOrder);
        }
    }

    public List<Trade> getExecutedTrades() {
        return executedTrades;
    }

    public List<Order> getOpenBuyOrders() {
        return new ArrayList<>(buyOrders);
    }

    public List<Order> getOpenSellOrders() {
        return new ArrayList<>(sellOrders);
    }
}
