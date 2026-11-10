package com.colortintes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Consulta los productos para mostrarlos en la pagina web.
    public List<Producto> listarProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        String sql = """
                SELECT id_producto, nombre, marca, color,
                       precio_unitario, id_categoria, id_proveedor
                FROM producto
                ORDER BY id_producto
                """;

        try (Connection conexion = Conexion.abrir();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet filas = consulta.executeQuery()) {

            while (filas.next()) {
                Producto producto = new Producto(
                        filas.getInt("id_producto"),
                        filas.getString("nombre"),
                        filas.getString("marca"),
                        filas.getString("color"),
                        filas.getBigDecimal("precio_unitario"),
                        filas.getInt("id_categoria"),
                        filas.getInt("id_proveedor")
                );

                productos.add(producto);
            }
        }

        return productos;
    }

    // Registra un producto y devuelve su identificador.
    public int registrarProducto(Producto producto) throws SQLException {
        String sql = """
                INSERT INTO producto
                    (nombre, marca, color, precio_unitario,
                     id_categoria, id_proveedor)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = Conexion.abrir();
             PreparedStatement consulta = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            consulta.setString(1, producto.nombre());
            consulta.setString(2, producto.marca());
            consulta.setString(3, producto.color());
            consulta.setBigDecimal(4, producto.precioUnitario());
            consulta.setInt(5, producto.idCategoria());
            consulta.setInt(6, producto.idProveedor());

            consulta.executeUpdate();

            try (ResultSet claves = consulta.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }

                throw new SQLException(
                        "No se recibio el ID del producto."
                );
            }
        }
    }

    // Actualiza el precio de un producto.
    public boolean actualizarPrecio(int idProducto, BigDecimal nuevoPrecio)
            throws SQLException {

        String sql = """
                UPDATE producto
                SET precio_unitario = ?
                WHERE id_producto = ?
                """;

        try (Connection conexion = Conexion.abrir();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setBigDecimal(1, nuevoPrecio);
            consulta.setInt(2, idProducto);

            return consulta.executeUpdate() == 1;
        }
    }

    // Elimina un producto por su identificador.
    public boolean eliminarProducto(int idProducto) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try (Connection conexion = Conexion.abrir();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idProducto);

            return consulta.executeUpdate() == 1;
        }
    }
}
