package com.tareacrud.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

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
    
    @Column(name = "direccion_social", length = 200)
    private String direccionSocial;

    @Column(name = "razon_social", length = 100)
    private String razonSocial;

    @Column(name = "capital_social")
    private Long capitalSocial;

    @Column(name = "fecha_constitucion")
    private Date fechaConstitucion;
}
