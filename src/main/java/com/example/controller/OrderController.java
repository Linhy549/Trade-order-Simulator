package com.example.controller;

import com.example.model.Order;
import com.example.model.OrderHistorySnapshot;
import com.example.model.Trade;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody Order order) {
        // Add a timestamp if not supplied
        if (order.getTimestamp() == null) {
            order.setTimestamp(LocalDateTime.now());
        }

        // Forward to service layer (matching + Kafka)
        service.processOrder(order);

        return "Order received: " + order.getOrderId();
    }

    @GetMapping("/trades")
    public List<Trade> getExecutedTrades() {
        return service.getExecutedTrades();
    }

    @GetMapping("/orderHistory")
    public OrderHistorySnapshot getOrderBook() {
        return new OrderHistorySnapshot(
                service.getOpenBuyOrders(),
                service.getOpenSellOrders()
        );
    }
}
