package com.concesionarioapp.conapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class coche {

    @Id
    private Long id;

    private String fabricante;
    private String modelo;
    private int year;
    private double precio;

    public Car() {} // tiene q haber uno vacio para el jpa

    // public Car() {
    // }
}
