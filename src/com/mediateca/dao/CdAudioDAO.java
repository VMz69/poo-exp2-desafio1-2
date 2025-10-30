package com.mediateca.dao;

import com.mediateca.modelo.CdAudio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class CdAudioDAO {
    private static final Logger log = LogManager.getLogger(CdAudioDAO.class);

    public void insertarCd(CdAudio cd) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlAudiovisual = "INSERT INTO material_audiovisual (codigo, duracion, unidades_disponibles, genero) VALUES (?, ?, ?, ?)";
        String sqlCd = "INSERT INTO cd_audio (codigo, artista, numero_canciones) VALUES (?, ?, ?)";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando insercion de CD de Audio con codigo: {}", cd.getCodigo());
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlAudiovisual);
            stmt3 = conn.prepareStatement(sqlCd);

            stmt1.setString(1, cd.getCodigo());
            stmt1.setString(2, cd.getTitulo());
            stmt1.executeUpdate();

            stmt2.setString(1, cd.getCodigo());
            stmt2.setInt(2, cd.getDuracion());
            stmt2.setInt(3, cd.getUnidadesDisponibles());
            stmt2.setString(4, cd.getGenero());
            stmt2.executeUpdate();

            stmt3.setString(1, cd.getCodigo());
            stmt3.setString(2, cd.getArtista());
            stmt3.setInt(3, cd.getNumeroCanciones());
            stmt3.executeUpdate();

            conn.commit();
            log.info("Se ha insertado correctamente el CD de Audio con codigo: {}", cd.getCodigo());
        } catch (SQLException e) {
            log.error("Error al insertar el CD de Audio con codigo: {} - {}", cd.getCodigo(), e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(conn);
        }
    }

    public ArrayList<CdAudio> listarCds() throws SQLException {
        ArrayList<CdAudio> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT m.codigo, m.titulo, ma.duracion, ma.unidades_disponibles, ma.genero,\n" +
                "       c.artista, c.numero_canciones\n" +
                "FROM cd_audio c\n" +
                "JOIN material_audiovisual ma ON c.codigo = ma.codigo\n" +
                "JOIN material m ON ma.codigo = m.codigo";

        try {
            conn = ConexionBD.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            log.info("Listando todos los registros de CD de Audio");
            while (rs.next()) {
                CdAudio cd = new CdAudio(
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getInt("duracion"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("genero"),
                        rs.getString("artista"),
                        rs.getInt("numero_canciones")
                );
                lista.add(cd);
            }
            log.info("Listado {} registros de CD de Audio", lista.size());
        } catch (SQLException e) {
            log.error("Error al listar los registros de CD de Audio: {}", e.getMessage());
            throw e;
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(stmt);
            ConexionBD.cerrar(conn);
        }
        return lista;
    }

    public void actualizarCd(CdAudio cd) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlAudiovisual = "UPDATE material_audiovisual SET duracion = ?, unidades_disponibles = ?, genero = ? WHERE codigo = ?";
        String sqlCd = "UPDATE cd_audio SET artista = ?, numero_canciones = ? WHERE codigo = ?";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando actualización de CD de Audio con código: {}", cd.getCodigo());
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlAudiovisual);
            stmt3 = conn.prepareStatement(sqlCd);

            stmt1.setString(1, cd.getTitulo());
            stmt1.setString(2, cd.getCodigo());
            stmt1.executeUpdate();

            stmt2.setInt(1, cd.getDuracion());
            stmt2.setInt(2, cd.getUnidadesDisponibles());
            stmt2.setString(3, cd.getGenero());
            stmt2.setString(4, cd.getCodigo());
            stmt2.executeUpdate();

            stmt3.setString(1, cd.getArtista());
            stmt3.setInt(2, cd.getNumeroCanciones());
            stmt3.setString(3, cd.getCodigo());
            stmt3.executeUpdate();

            conn.commit();
            log.info("Actualización exitosa de CD de Audio con codigo: {}", cd.getCodigo());
        } catch (SQLException e) {
            log.error("Error al actualizar el CD de Audio con codigo: {} - {}", cd.getCodigo(), e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(conn);
        }
    }

    public void eliminarCd(String codigo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "DELETE FROM material WHERE codigo = ?";
        String sqlAudiovisual = "DELETE FROM material_audiovisual WHERE codigo = ?";
        String sqlCd = "DELETE FROM cd_audio WHERE codigo = ?";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando eliminacion de CD de Audio con codigo: {}", codigo);
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlAudiovisual);
            stmt3 = conn.prepareStatement(sqlCd);

            stmt1.setString(1, codigo);
            stmt1.executeUpdate();
            stmt2.setString(1, codigo);
            stmt2.executeUpdate();
            stmt3.setString(1, codigo);
            stmt3.executeUpdate();

            conn.commit();
            log.info("Eliminacion exitosa de CD de Audio con codigo: {}", codigo);
        } catch (SQLException e) {
            log.error("Error al eliminar el CD de Audio con codigo: {} - {}", codigo, e.getMessage());
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