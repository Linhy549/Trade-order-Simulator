package com.example.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "orders")
public class Order {
	@Id
    private String orderId;
	@Enumerated(EnumType.STRING)
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
