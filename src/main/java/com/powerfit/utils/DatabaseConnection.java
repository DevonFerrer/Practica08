package com.powerfit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configuramos el puerto 3307 como indicaste
    private static final String URL = "jdbc:mysql://localhost:3307/powerfit_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Tu usuario de MySQL
    private static final String PASS = "";     // Tu contraseña de MySQL

    static {
        try {
            // Cargamos el driver de MySQL (asegúrate de tener el conector en tus dependencias)
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Método para probar la conexión en consola
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("¡Conexión exitosa a la base de datos en el puerto 3307!");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }
}