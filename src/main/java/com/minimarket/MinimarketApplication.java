package com.minimarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinimarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinimarketApplication.class, args);
        System.out.println("\n=========================================");
        System.out.println("🚀 MINIMARKET BACKEND INICIADO CORRECTAMENTE");
        System.out.println("=========================================");
        System.out.println("🌐 URL: http://localhost:8080/api");
        System.out.println("📊 Estado: http://localhost:8080/api/public/status");
        System.out.println("🔑 Login: POST http://localhost:8080/api/auth/signin");
        System.out.println("   Usuario: admin");
        System.out.println("   Contraseña: admin123");
        System.out.println("=========================================\n");
    }
}