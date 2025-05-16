package com.example.backAnana.Entities.Enums;

public enum Localidad {
    // Departamentos de Mendoza
    CAPITAL("Ciudad de Mendoza"),
    GODOY_CRUZ("Godoy Cruz"),
    GUAYMALLEN("Guaymallén"),
    LAS_HERAS("Las Heras"),
    LUJAN_DE_CUYO("Luján de Cuyo"),
    MAIPU("Maipú"),
    MENDOZA("Mendoza"), // (parte del Gran Mendoza)
    SAN_MARTIN("San Martín"),
    SAN_RAFAEL("San Rafael"),
    TUNUYAN("Tunuyán"),
    TUPUNGATO("Tupungato"),
    GENERAL_ALVEAR("General Alvear"),
    JUNIN("Junín"),
    LA_PAZ("La Paz"),
    LAVALLE("Lavalle"),
    MALARGUE("Malargüe"),
    RIVADAVIA("Rivadavia"),
    SAN_CARLOS("San Carlos"),
    SANTA_ROSA("Santa Rosa");

    private String nombre;

    private Localidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
