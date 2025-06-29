package com.example.repository;


import com.example.model.Order;
import com.example.model.OrderType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	//TODO: Define custom query methods e.g. List<Order> dingByType(OrderType type)
	List<Order> findByType(OrderType type);
}
