package com.example.controller;

import com.example.model.Trade;
import com.example.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trades")
public class TradeController {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeController(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/allTrades")
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable String id) {
        if (tradeRepository.existsById(id)) {
            tradeRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
