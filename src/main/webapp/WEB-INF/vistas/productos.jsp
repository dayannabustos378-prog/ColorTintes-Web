<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ColorTintes | Productos</title>

    <style>
        * { box-sizing: border-box; }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f7f3fa;
            color: #30243b;
        }

        header {
            background: #642b80;
            color: white;
            padding: 28px 20px;
            text-align: center;
        }

        header h1 { margin: 0 0 8px; }
        header p { margin: 0; }

        main {
            max-width: 1100px;
            margin: 28px auto;
            padding: 0 16px;
        }

        section {
            background: white;
            padding: 24px;
            margin-bottom: 24px;
            border-radius: 12px;
            box-shadow: 0 3px 12px #00000010;
        }

        h2 { margin-top: 0; }

        .campos {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 18px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 7px;
        }

        input {
            width: 100%;
            padding: 11px;
            border: 1px solid #b8aabd;
            border-radius: 6px;
            font: inherit;
        }

        button {
            background: #642b80;
            color: white;
            border: 0;
            border-radius: 6px;
            padding: 12px 20px;
            font: inherit;
            cursor: pointer;
            margin-top: 18px;
        }

        button:hover { background: #49205d; }

        .mensaje {
            padding: 14px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .exito { background: #e0f4e7; color: #18552d; }
        .error { background: #fde5e5; color: #842020; }

        .ayuda { color: #64566b; line-height: 1.5; }

        .tabla { overflow-x: auto; }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #e8e0ec;
        }

        th { background: #f0e7f5; }

        footer {
            text-align: center;
            padding: 20px;
            color: #64566b;
        }

        @media (max-width: 600px) {
            .campos { grid-template-columns: 1fr; }
            section { padding: 18px; }
        }
    </style>
</head>

<body>
<header>
    <h1>ColorTintes</h1>
    <p>Gestión de productos de belleza y coloración</p>
</header>

<main>
    <c:url var="productosUrl" value="/productos" />

    <c:if test="${param.guardado == '1' and empty error}">
        <div class="mensaje exito" role="status">
            Producto registrado correctamente.
        </div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="mensaje error" role="alert">
            <c:out value="${error}" />
        </div>
    </c:if>

    <section>
        <h2>Registrar producto</h2>
        <p class="ayuda">Todos los campos son obligatorios.</p>

        <form action="${productosUrl}" method="post">
            <div class="campos">
                <div>
                    <label for="nombre">Nombre del producto</label>
                    <input id="nombre" name="nombre"
                           type="text" required>
                </div>

                <div>
                    <label for="marca">Marca</label>
                    <input id="marca" name="marca"
                           type="text" required>
                </div>

                <div>
                    <label for="color">Color o tono</label>
                    <input id="color" name="color"
                           type="text" required>
                </div>

                <div>
                    <label for="precioUnitario">Precio unitario (COP)</label>
                    <input id="precioUnitario" name="precioUnitario"
                           type="number" min="0.01" step="0.01"
                           required>
                </div>

                <div>
                    <label for="idCategoria">ID de categoría</label>
                    <input id="idCategoria" name="idCategoria"
                           type="number" min="1" step="1"
                           aria-describedby="ayudaIds" required>
                </div>

                <div>
                    <label for="idProveedor">ID de proveedor</label>
                    <input id="idProveedor" name="idProveedor"
                           type="number" min="1" step="1"
                           aria-describedby="ayudaIds" required>
                </div>
            </div>

            <p id="ayudaIds" class="ayuda">
                La categoría y el proveedor deben existir
                previamente en la base de datos.
            </p>

            <button type="submit">Guardar producto</button>
        </form>
    </section>

    <section>
        <h2>Productos registrados</h2>

        <form action="${productosUrl}" method="get">
            <button type="submit">Actualizar listado</button>
        </form>

        <c:choose>
            <c:when test="${empty productos}">
                <p>Todavía no hay productos registrados.</p>
            </c:when>

            <c:otherwise>
                <div class="tabla">
                    <table>
                        <caption>Listado de productos de ColorTintes</caption>
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Marca</th>
                                <th scope="col">Color</th>
                                <th scope="col">Precio (COP)</th>
                                <th scope="col">Categoría</th>
                                <th scope="col">Proveedor</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="producto" items="${productos}">
                                <tr>
                                    <td><c:out value="${producto.idProducto}" /></td>
                                    <td><c:out value="${producto.nombre}" /></td>
                                    <td><c:out value="${producto.marca}" /></td>
                                    <td><c:out value="${producto.color}" /></td>
                                    <td><c:out value="${producto.precioUnitario}" /></td>
                                    <td><c:out value="${producto.idCategoria}" /></td>
                                    <td><c:out value="${producto.idProveedor}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<footer>
    ColorTintes · Gestión de productos
</footer>
</body>
</html>

