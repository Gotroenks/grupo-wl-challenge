<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Grupo WL</title>
    </head>
    <body>
        <%@ page import="com.example.challengegrupowl.controller.testControllerRest, java.util.*" %>
        <%@ page import="com.example.challengegrupowl.model.users" %>
        <%@ page import="java.sql.SQLException" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <h1>Lista dos usuarios</h1>

        <%
            List<users> listUsers = null;
            try {
                listUsers = testControllerRest.getAllColaboradores();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("list", listUsers);
        %>

        <table>
            <tr>
                <th>CPF</th>
                <th>NOME</th>
                <th>ITEM</th>
            </tr>
        </table>
    </body>
</html>
