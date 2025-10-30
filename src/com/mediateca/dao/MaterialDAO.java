package com.mediateca.dao;

import com.mediateca.modelo.Material;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class MaterialDAO{
    private static final Logger log =  LogManager.getLogger(MaterialDAO.class);

    public ArrayList<Material> listarMateriales() throws SQLException {
        ArrayList<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM material";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            log.info("Listando todos los registros de com.mediateca.modelo.Material");
            while (rs.next()) {
                Material material = new Material(
                        rs.getString("codigo"),
                        rs.getString("titulo")
                ) {
                    @Override
                    public String getTipo() {
                        return "";
                    }
                };
                lista.add(material);
            }
            log.info("Listando {} registros de com.mediateca.modelo.Material", lista.size());
        } catch (SQLException e){
            log.error("Error al listar los registros de com.mediateca.modelo.Libro: {}", e.getMessage());
            throw e;
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(stmt);
            ConexionBD.cerrar(conn);
        }
        return lista;
    }


    public ArrayList<Material> buscarMateriales(String textoBusqueda) throws SQLException {
        ArrayList<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM material WHERE codigo LIKE ? OR titulo LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.conectar();
            pstmt = conn.prepareStatement(sql);

            String criterio = "%" + textoBusqueda + "%";
            pstmt.setString(1, criterio);
            pstmt.setString(2, criterio);

            rs = pstmt.executeQuery();
            log.info("Buscando registros de com.mediateca.modelo.Material con texto: {}", textoBusqueda);
            while (rs.next()) {
                Material material = new Material(
                        rs.getString("codigo"),
                        rs.getString("titulo")
                ) {
                    @Override
                    public String getTipo() {
                            return "";
                        }
                };
                lista.add(material);
            }
            log.info("Encontrados {} registros para el texto de búsqueda", lista.size());
        } catch (SQLException e) {
            log.error("Error al buscar los registros de com.mediateca.modelo.Material: {}", e.getMessage());
            throw e;
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(pstmt);
            ConexionBD.cerrar(conn);
        }
        return lista;
    }
}