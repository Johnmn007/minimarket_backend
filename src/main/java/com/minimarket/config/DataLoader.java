package com.minimarket.config;

import com.minimarket.entity.ERole;
import com.minimarket.entity.Role;
import com.minimarket.entity.Usuario;
import com.minimarket.repository.RoleRepository;
import com.minimarket.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DataLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository, 
                                   UsuarioRepository usuarioRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear roles si no existen
            if (roleRepository.count() == 0) {
                logger.info("Creando roles iniciales...");
                
                Role adminRole = new Role(ERole.ADMIN);
                Role cajeroRole = new Role(ERole.CAJERO);
                Role almaceneroRole = new Role(ERole.ALMACENERO);
                
                roleRepository.save(adminRole);
                roleRepository.save(cajeroRole);
                roleRepository.save(almaceneroRole);
                
                logger.info("Roles creados: ADMIN, CAJERO, ALMACENERO");
            }
            
            // Crear usuario admin si no existe
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                logger.info("Creando usuario administrador...");
                
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setActivo(true);
                
                Role adminRole = roleRepository.findByNombre(ERole.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Rol ADMIN no encontrado"));
                
                admin.getRoles().add(adminRole);
                
                usuarioRepository.save(admin);
                logger.info("Usuario admin creado - username: admin, password: admin123");
            }
            
            logger.info("Inicialización de datos completada");
        };
    }
}