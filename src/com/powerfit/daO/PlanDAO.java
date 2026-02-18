package com.powerfit.dao;
import com.powerfit.config.Conexion;
import com.powerfit.model.Plan;
import java.sql.*;
import java.util.*;

public class PlanDAO {
    public List<Plan> listar() {
        List<Plan> lista = new ArrayList<>();
        String sql = "SELECT * FROM planes";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("id_plan"));
                p.setNombre(rs.getString("nombre_plan"));
                p.setPrecio(rs.getDouble("precio"));
                lista.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}
