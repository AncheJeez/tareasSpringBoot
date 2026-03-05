package com.tareaspring.demo.service;

public class CiudadUsuariosStats {

    private final String ciudad;
    private final long total;
    private final int porcentaje;

    public CiudadUsuariosStats(String ciudad, long total, int porcentaje) {
        this.ciudad = ciudad;
        this.total = total;
        this.porcentaje = porcentaje;
    }

    public String getCiudad() {
        return ciudad;
    }

    public long getTotal() {
        return total;
    }

    public int getPorcentaje() {
        return porcentaje;
    }
}
