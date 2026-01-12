package com.minimarket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class TestController {
    
    @GetMapping("/status")
    public String getStatus() {
        return "✅ Minimarket Backend funcionando - " + System.currentTimeMillis();
    }
}