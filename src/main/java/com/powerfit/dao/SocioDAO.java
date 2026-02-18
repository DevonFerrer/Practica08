package com.powerfit.dao;

import com.powerfit.models.Socio;
import com.powerfit.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones CRUD de Socios en el sistema PowerFit
 */
public class SocioDAO {

    /**
     * Inserta un nuevo socio en la base de datos
     */
    public boolean crear(Socio socio) throws SQLException {
        String sql = "INSERT INTO socios (nombre_completo, dni, email, telefono, id_plan, fecha_vencimiento) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, socio.getNombreCompleto());
            pstmt.setString(2, socio.getDni());
            pstmt.setString(3, socio.getEmail());
            pstmt.setString(4, socio.getTelefono());
            pstmt.setInt(5, socio.getIdPlan());
            pstmt.setDate(6, java.sql.Date.valueOf(socio.getFechaVencimiento()));
            
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Busca un socio por su ID único
     */
    public Socio obtenerPorId(int idSocio) throws SQLException {
        String sql = "SELECT * FROM socios WHERE id_socio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idSocio);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        }
        return null;
    }

    /**
     * Recupera la lista completa de socios registrados
     */
    public List<Socio> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM socios ORDER BY id_socio";
        List<Socio> socios = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                socios.add(mapearResultSet(rs));
            }
        }
        return socios;
    }

    /**
     * Actualiza los datos de un socio existente
     */
    public boolean actualizar(Socio socio) throws SQLException {
        String sql = "UPDATE socios SET nombre_completo = ?, email = ?, telefono = ?, " +
                     "id_plan = ?, estado = ?, fecha_vencimiento = ? WHERE id_socio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, socio.getNombreCompleto());
            pstmt.setString(2, socio.getEmail());
            pstmt.setString(3, socio.getTelefono());
            pstmt.setInt(4, socio.getIdPlan());
            pstmt.setString(5, socio.getEstado());
            pstmt.setDate(6, java.sql.Date.valueOf(socio.getFechaVencimiento()));
            pstmt.setInt(7, socio.getIdSocio());
            
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Elimina a un socio de la base de datos por su ID
     */
    public boolean eliminar(int idSocio) throws SQLException {
        String sql = "DELETE FROM socios WHERE id_socio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idSocio);
            return pstmt.executeUpdate() > 0;
        }
    }

    /**
     * Método auxiliar para convertir un ResultSet en un objeto Socio
     */
    private Socio mapearResultSet(ResultSet rs) throws SQLException {
        Socio socio = new Socio();
        socio.setIdSocio(rs.getInt("id_socio"));
        socio.setNombreCompleto(rs.getString("nombre_completo"));
        socio.setDni(rs.getString("dni"));
        socio.setEmail(rs.getString("email"));
        socio.setTelefono(rs.getString("telefono"));
        socio.setIdPlan(rs.getInt("id_plan"));
        socio.setEstado(rs.getString("estado"));
        
        // Manejo de fechas y tiempos de Java 8
        Timestamp ts = rs.getTimestamp("fecha_registro");
        if (ts != null) {
            socio.setFechaRegistro(ts.toLocalDateTime());
        }
        
        Date date = rs.getDate("fecha_vencimiento");
        if (date != null) {
            socio.setFechaVencimiento(date.toLocalDate());
        }
        
        return socio;
    }
}