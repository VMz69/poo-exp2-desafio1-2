package com.mediateca.dao;

import com.mediateca.modelo.Dvd;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class DvdDAO {
    private static final Logger log = LogManager.getLogger(DvdDAO.class);

    public void insertarDvd(Dvd dvd) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlAudiovisual = "INSERT INTO material_audiovisual (codigo, duracion, unidades_disponibles, genero) VALUES (?, ?, ?, ?)";
        String sqlDvd = "INSERT INTO dvd (codigo, director) VALUES (?, ?)";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando insercion de DVD con codigo: {}", dvd.getCodigo());
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlAudiovisual);
            stmt3 = conn.prepareStatement(sqlDvd);

            stmt1.setString(1, dvd.getCodigo());
            stmt1.setString(2, dvd.getTitulo());
            stmt1.executeUpdate();

            stmt2.setString(1, dvd.getCodigo());
            stmt2.setInt(2, dvd.getDuracion());
            stmt2.setInt(3, dvd.getUnidadesDisponibles());
            stmt2.setString(4, dvd.getGenero());
            stmt2.executeUpdate();

            stmt3.setString(1, dvd.getCodigo());
            stmt3.setString(2, dvd.getDirector());
            stmt3.executeUpdate();

            conn.commit();
            log.info("Se ha insertado correctamente el DVD con codigo: {}", dvd.getCodigo());
        } catch (SQLException e) {
            log.error("Error al insertar el DVD con codigo: {} - {}", dvd.getCodigo(), e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(conn);
        }
    }

    public ArrayList<Dvd> listarDvds() throws SQLException {
        ArrayList<Dvd> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT m.codigo, m.titulo, ma.duracion, ma.unidades_disponibles, ma.genero,\n" +
                "       d.director\n" +
                "FROM dvd d\n" +
                "JOIN material_audiovisual ma ON d.codigo = ma.codigo\n" +
                "JOIN material m ON ma.codigo = m.codigo";

        try {
            conn = ConexionBD.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            log.info("Listando todos los registros de DVD");
            while (rs.next()) {
                Dvd dvd = new Dvd(
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getInt("duracion"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("genero"),
                        rs.getString("director")
                );
                lista.add(dvd);
            }
            log.info("Listado {} registros de DVD", lista.size());
        } catch (SQLException e) {
            log.error("Error al listar los registros de DVD: {}", e.getMessage());
            throw e;
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(stmt);
            ConexionBD.cerrar(conn);
        }
        return lista;
    }

    public void actualizarDvd(Dvd dvd) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlAudiovisual = "UPDATE material_audiovisual SET duracion = ?, unidades_disponibles = ?, genero = ? WHERE codigo = ?";
        String sqlDvd = "UPDATE dvd SET director = ? WHERE codigo = ?";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando actualizacion de DVD con codigo: {}", dvd.getCodigo());
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlAudiovisual);
            stmt3 = conn.prepareStatement(sqlDvd);

            stmt1.setString(1, dvd.getTitulo());
            stmt1.setString(2, dvd.getCodigo());
            stmt1.executeUpdate();

            stmt2.setInt(1, dvd.getDuracion());
            stmt2.setInt(2, dvd.getUnidadesDisponibles());
            stmt2.setString(3, dvd.getGenero());
            stmt2.setString(4, dvd.getCodigo());
            stmt2.executeUpdate();

            stmt3.setString(1, dvd.getDirector());
            stmt3.setString(2, dvd.getCodigo());
            stmt3.executeUpdate();

            conn.commit();
            log.info("Actualizacion exitosa de DVD con codigo: {}", dvd.getCodigo());
        } catch (SQLException e) {
            log.error("Error al actualizar el DVD con codigo: {} - {}", dvd.getCodigo(), e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(conn);
        }
    }

    public void eliminarDvd(String codigo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "DELETE FROM material WHERE codigo = ?";
        String sqlAudiovisual = "DELETE FROM material_audiovisual WHERE codigo = ?";
        String sqlDvd = "DELETE FROM dvd WHERE codigo = ?";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando eliminacion de DVD con codigo: {}", codigo);
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlAudiovisual);
            stmt3 = conn.prepareStatement(sqlDvd);

            stmt1.setString(1, codigo); stmt1.executeUpdate();
            stmt2.setString(1, codigo); stmt2.executeUpdate();
            stmt3.setString(1, codigo); stmt3.executeUpdate();

            conn.commit();
            log.info("Eliminacion exitosa de DVD con codigo: {}", codigo);
        } catch (SQLException e) {
            log.error("Error al eliminar el DVD con codigo: {} - {}", codigo, e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(conn);
        }
    }
}