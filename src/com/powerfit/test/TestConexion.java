package com.powerfit.test;

import com.powerfit.config.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConexion {
    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            System.out.println("Intentando conectar a powerfit_db...");
            con = Conexion.getConexion();

            if (con != null) {
                System.out.println("¡CONEXIÓN EXITOSA!");
                
               
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM planes");
                   
            } else {
                System.out.println("ERROR: La conexión retornó NULL.");
            }
        } catch (Exception e) {
            System.out.println("ERROR CRÍTICO: " + e.getMessage());
        } finally {
            
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar: " + e.getMessage());
            }
        }
    }
}
