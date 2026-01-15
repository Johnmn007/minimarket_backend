package com.minimarket.controller;

import com.minimarket.dto.request.LoginRequest;
import com.minimarket.dto.response.JwtResponse;
import com.minimarket.security.jwt.JwtUtils;
import com.minimarket.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Intento de login para usuario: {}", loginRequest.getUsername());
            
            // Autenticar al usuario con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Generar token JWT
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            // Obtener detalles del usuario autenticado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            
            // Obtener roles
            List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
            
            logger.info("Login exitoso para usuario: {} con roles: {}", userDetails.getUsername(), roles);
            
            // Crear respuesta
            JwtResponse response = new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles
            );
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            logger.error("Credenciales inválidas para usuario: {}", loginRequest.getUsername());
            Map<String, String> error = new HashMap<>();
            error.put("message", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception e) {
            logger.error("Error en autenticación: ", e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error en el proceso de autenticación");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> testAuth() {
        return ResponseEntity.ok("Auth test endpoint works!");
    }
    
    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> response = new HashMap<>();
        
        if (authentication != null && authentication.isAuthenticated()) {
            response.put("valid", "true");
            response.put("username", authentication.getName());
            return ResponseEntity.ok(response);
        }
        
        response.put("valid", "false");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}