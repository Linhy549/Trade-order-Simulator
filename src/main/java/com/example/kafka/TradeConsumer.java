package com.example.kafka;

import com.example.model.Trade;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TradeConsumer {

	@KafkaListener(topics = "trades", groupId = "trading-group")
	public void listen(Trade trade) {
	    System.out.println("Received trade: " + trade);
	}
}