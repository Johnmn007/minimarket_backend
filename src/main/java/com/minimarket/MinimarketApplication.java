package com.minimarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinimarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinimarketApplication.class, args);
        System.out.println("🚀 Minimarket Backend ejecutándose en http://localhost:8080");
    }
}