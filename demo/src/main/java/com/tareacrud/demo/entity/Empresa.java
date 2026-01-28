package com.tareacrud.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa")
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    
    @Column(name = "nif", nullable = false, unique = true, length = 20)
    private String nif;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "direccion", length = 255)
    private String direccion;
    
    @Column(name = "ciudad", length = 50)
    private String ciudad;
    
    @Column(name = "pais", length = 50)
    private String pais;
    
    @Column(name = "activa", nullable = false)
    private Boolean activa = true;
}
