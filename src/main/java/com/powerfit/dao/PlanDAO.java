package com.powerfit.dao;

import com.powerfit.models.Plan;
import com.powerfit.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {

    public List<Plan> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM planes ORDER BY id_plan";
        List<Plan> planes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                planes.add(mapearResultSet(rs));
            }
        }
        return planes;
    }

    public Plan obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM planes WHERE id_plan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        }
        return null;
    }

    // Métodos adicionales que requiere tu servidor
    public boolean crear(Plan plan) throws SQLException {
        String sql = "INSERT INTO planes (nombre_plan, descripcion, precio, duracion_meses) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plan.getNombrePlan());
            pstmt.setString(2, plan.getDescripcion());
            pstmt.setBigDecimal(3, plan.getPrecio());
            pstmt.setInt(4, plan.getDuracionMeses());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Plan plan) throws SQLException {
        String sql = "UPDATE planes SET nombre_plan=?, descripcion=?, precio=?, duracion_meses=? WHERE id_plan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plan.getNombrePlan());
            pstmt.setString(2, plan.getDescripcion());
            pstmt.setBigDecimal(3, plan.getPrecio());
            pstmt.setInt(4, plan.getDuracionMeses());
            pstmt.setInt(5, plan.getIdPlan());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM planes WHERE id_plan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Plan mapearResultSet(ResultSet rs) throws SQLException {
        Plan p = new Plan();
        p.setIdPlan(rs.getInt("id_plan"));
        p.setNombrePlan(rs.getString("nombre_plan"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getBigDecimal("precio"));
        p.setDuracionMeses(rs.getInt("duracion_meses"));
        return p;
    }
}