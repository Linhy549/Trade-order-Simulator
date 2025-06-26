package com.example.model;

import java.util.List;

public class OrderHistorySnapshot {
    private List<Order> buyOrders;
    private List<Order> sellOrders;

    public OrderHistorySnapshot(List<Order> buyOrders, List<Order> sellOrders) {
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    public List<Order> getBuyOrders() {
        return buyOrders;
    }

    public void setBuyOrders(List<Order> buyOrders) {
        this.buyOrders = buyOrders;
    }

    public List<Order> getSellOrders() {
        return sellOrders;
    }

    public void setSellOrders(List<Order> sellOrders) {
        this.sellOrders = sellOrders;
    }
}