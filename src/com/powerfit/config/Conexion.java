/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.powerfit.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    // 1. Nota el ".cj." en el nombre del driver (para MySQL 8+)
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // 2. Agregamos parámetros de seguridad a la URL
    private static final String URL = "jdbc:mysql://localhost:3306/powerfit_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = ""; // Tu contraseña de MySQL

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e);
        }
        return con;
    }
}