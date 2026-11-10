package com.colortintes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection abrir() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "No se encontro el controlador de MariaDB.", e
            );
        }

        String url = "jdbc:mariadb://localhost:3306/colortintes_db";
        String usuario = "root";
        String clave = "";

        return DriverManager.getConnection(url, usuario, clave);
    }
}
    