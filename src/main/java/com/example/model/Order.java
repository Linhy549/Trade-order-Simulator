package com.example.model;

import java.time.LocalDateTime;

public class Order {
    private String orderId;
    private OrderType type;
    private double price;
    private int quantity;
    private LocalDateTime timestamp;

    public Order(String orderId, OrderType type, double price, int quantity) {
        this.orderId = orderId;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public Order (){

    }

    // Constructors, Getters, Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
