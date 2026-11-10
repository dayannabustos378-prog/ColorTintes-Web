package com.colortintes;

import java.math.BigDecimal;

public record Producto(
        int idProducto,
        String nombre,
        String marca,
        String color,
        BigDecimal precioUnitario,
        int idCategoria,
        int idProveedor
) {
    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getIdProveedor() {
        return idProveedor;
    }
}