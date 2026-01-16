package com.minimarket.security;

import com.minimarket.security.jwt.AuthEntryPointJwt;
import com.minimarket.security.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Habilitar CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Deshabilitar CSRF (APIs REST stateless)
                .csrf(csrf -> csrf.disable())

                // Manejar excepciones de autenticación
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler))

                // Sin estado (stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configurar autorizaciones
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // Endpoints protegidos
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/vendedor/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                        .requestMatchers("/api/almacen/**").hasAnyAuthority("ADMIN", "ALMACENERO")

                        // Cualquier otro requiere autenticación
                        .anyRequest().authenticated())

                // Proveedor de autenticación
                .authenticationProvider(authenticationProvider())

                // Filtro JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Orígenes permitidos
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5500",
                "http://127.0.0.1:5500",
                "http://localhost:8080",
                "http://localhost:3000",
                "http://localhost:4200", // Angular
                "http://localhost:5173" // Angular
        ));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Headers expuestos al cliente
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        
        // Permitir credenciales
        configuration.setAllowCredentials(true);
        
        // Tiempo de cache para preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) 
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}