package com.example.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.example.model.Trade;

public class TradeExporter {

    public static void exportToCSV(List<Trade> trades, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("tradeId,buyOrderId,sellOrderId,price,quantity,timestamp\n");

            for (Trade trade : trades) {
                writer.append(trade.getTradeId()).append(",");
                writer.append(trade.getBuyOrderId()).append(",");
                writer.append(trade.getSellOrderId()).append(",");
                writer.append(String.valueOf(trade.getPrice())).append(",");
                writer.append(String.valueOf(trade.getQuantity())).append(",");
                writer.append(trade.getTimestamp().toString()).append("\n");
            }

            System.out.println("Trade results exported to: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to export trades: " + e.getMessage());
        }
    }
}
