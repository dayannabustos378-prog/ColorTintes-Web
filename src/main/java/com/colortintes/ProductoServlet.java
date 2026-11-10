package com.colortintes;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();

    // GET: muestra el formulario y la lista de productos.
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute(
                    "productos", productoDAO.listarProductos()
            );

            request.getRequestDispatcher(
                    "/WEB-INF/vistas/productos.jsp"
            ).forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "No fue posible consultar los productos.", e
            );
        }
    }

    // POST: valida y registra los datos del formulario.
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String nombre = leerTexto(request, "nombre");
            String marca = leerTexto(request, "marca");
            String color = leerTexto(request, "color");

            BigDecimal precio = new BigDecimal(
                    leerTexto(request, "precioUnitario")
            );

            int idCategoria = Integer.parseInt(
                    leerTexto(request, "idCategoria")
            );

            int idProveedor = Integer.parseInt(
                    leerTexto(request, "idProveedor")
            );

            if (precio.signum() <= 0
                    || idCategoria <= 0
                    || idProveedor <= 0) {

                throw new IllegalArgumentException(
                        "El precio, la categoria y el proveedor "
                        + "deben ser mayores que cero."
                );
            }

            Producto producto = new Producto(
                    0, nombre, marca, color, precio,
                    idCategoria, idProveedor
            );

            productoDAO.registrarProducto(producto);

            response.sendRedirect(
                    request.getContextPath() + "/productos?guardado=1"
            );

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            request.setAttribute(
                    "error",
                    "Revisa los campos: todos son obligatorios. "
                    + "El precio debe ser un numero positivo y "
                    + "los identificadores deben ser enteros positivos."
            );

            doGet(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Error al registrar un producto.", e
            );

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            request.setAttribute(
                    "error",
                    "No se pudo guardar el producto. Comprueba que "
                    + "la categoria y el proveedor existan "
                    + "en la base de datos."
            );

            doGet(request, response);
        }
    }

    // Obtiene un campo y comprueba que tenga contenido.
    private String leerTexto(HttpServletRequest request, String campo) {
        String valor = request.getParameter(campo);

        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                    "El campo " + campo + " es obligatorio."
            );
        }

        return valor.trim();
    }
}
