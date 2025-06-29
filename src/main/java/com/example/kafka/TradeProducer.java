package com.example.kafka;

import com.example.model.Trade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service 
public class TradeProducer {
    private static final String TOPIC = "trades";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TradeProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void sendTrade(Trade trade) {
    	
    	try {
            String tradeJson = objectMapper.writeValueAsString(trade);
            kafkaTemplate.send("trades", trade.getTradeId(), tradeJson);
            System.out.println("Sent trade to Kafka: " + tradeJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
