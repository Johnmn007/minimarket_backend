package com.minimarket.entity;

import jakarta.persistence.*;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username")
       })
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @NotBlank
    // @Size(max = 20)
    @Column(unique = true, nullable = false)
    private String username;
    
    // @NotBlank
    // @Size(max = 120)
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private boolean activo = true;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_roles",
               joinColumns = @JoinColumn(name = "usuario_id"),
               inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Role> roles = new HashSet<>();
    
    // Constructores
    public Usuario() {}
    
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}