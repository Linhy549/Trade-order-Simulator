package com.example.kafka;

import com.example.model.Trade;
import org.springframework.kafka.core.KafkaTemplate;

public class TradeProducer {
    private static final String TOPIC = "trades";

    private final KafkaTemplate<String, Trade> kafkaTemplate;

    public TradeProducer(KafkaTemplate<String, Trade> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTrade(Trade trade) {
        kafkaTemplate.send(TOPIC, trade.getTradeId(), trade);
    }
}
