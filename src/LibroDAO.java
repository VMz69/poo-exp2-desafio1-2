import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;

public class LibroDAO {
    private static final Logger log =  LogManager.getLogger(LibroDAO.class);

    public void insertarLibro(Libro libro) throws SQLException {
        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlEscrito = "INSERT INTO material_escrito (codigo, editorial, unidades_disponibles) VALUES (?, ?, ?)";
        String sqlLibro = "INSERT INTO libro (codigo, autor, numero_paginas, isbn, anio_publicacion) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        try{
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando insercion de Libro con codigo: {}",  libro.getCodigo());
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlEscrito);
            stmt3 = conn.prepareStatement(sqlLibro);

            stmt1.setString(1, libro.getCodigo());
            stmt1.setString(2, libro.getTitulo());
            stmt1.executeUpdate();

            stmt2.setString(1, libro.getCodigo());
            stmt2.setString(2, libro.getEditorial());
            stmt2.setInt(3, libro.getUnidadesDisponibles());
            stmt2.executeUpdate();

            stmt3.setString(1, libro.getCodigo());
            stmt3.setString(2, libro.getAutor());
            stmt3.setInt(3, libro.getNumeroPaginas());
            stmt3.setString(4, libro.getIsbn());
            stmt3.setInt(5, libro.getAnioPublicacion());
            stmt3.executeUpdate();
            conn.commit();
            log.info("Se ha insertado correctamente el Libro con codigo: {}",  libro.getCodigo());
        } catch (SQLException e) {
            log.error("Error al insertar el Libro con codigo: {} - {}", libro.getCodigo(), e.getMessage());
            if(conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(conn);
        }
    }


    public ArrayList<Libro> listarLibros() throws SQLException {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "SELECT m.codigo, m.titulo, me.editorial, me.unidades_disponibles,\n" +
                        "       l.autor, l.numero_paginas, l.isbn, l.anio_publicacion\n" +
                        "FROM libro l\n" +
                        "JOIN material_escrito me ON l.codigo = me.codigo\n" +
                        "JOIN material m ON me.codigo = m.codigo";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            log.info("Listando todos los registros de Libro");
            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getString("editorial"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("autor"),
                        rs.getInt("numero_paginas"),
                        rs.getString("isbn"),
                        rs.getInt("anio_publicacion")
                );
                lista.add(libro);
            }
            log.info("Listando {} registros de Libro", lista.size());
        } catch (SQLException e){
            log.error("Error al listar los registros de Libro: {}", e.getMessage());
            throw e;
        } finally{
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(stmt);
            ConexionBD.cerrar(conn);
        }
        return lista;
    }

    public void actualizarLibro(Libro libro) throws SQLException {
        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlEscrito = "UPDATE material_escrito SET editorial = ?, unidades_disponibles = ? WHERE codigo = ?";
        String sqlLibro = "UPDATE libro SET autor = ?, numero_paginas = ?, isbn = ?, anio_publicacion = ? WHERE codigo = ?";
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando actualizacion de Libro con codigo: {}", libro.getCodigo());
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlEscrito);
            stmt3 = conn.prepareStatement(sqlLibro);

            stmt1.setString(1, libro.getTitulo());
            stmt1.setString(2, libro.getCodigo());
            stmt1.executeUpdate();

            stmt2.setString(1, libro.getEditorial());
            stmt2.setInt(2, libro.getUnidadesDisponibles());
            stmt2.setString(3, libro.getCodigo());
            stmt2.executeUpdate();

            stmt3.setString(1, libro.getAutor());
            stmt3.setInt(2, libro.getNumeroPaginas());
            stmt3.setString(3, libro.getIsbn());
            stmt3.setInt(4, libro.getAnioPublicacion());
            stmt3.setString(5, libro.getCodigo());
            stmt3.executeUpdate();

            conn.commit();
            log.info("Actualizacion exitosa de  Libro con codigo: {}", libro.getCodigo());
        } catch (SQLException e) {
            log.error("Error al actualizar Libro con codigo : {} - {}", libro.getCodigo(), e.getMessage());
            if(conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(conn);
        }
    }

    public void eliminarLibro(String codigo) throws SQLException {
        String sqlMaterial = "DELETE FROM material WHERE codigo = ?";
        String sqlEscrito = "DELETE FROM material_escrito WHERE codigo = ?";
        String sqlLibro = "DELETE FROM libro WHERE codigo = ?";
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando eliminacion de Libro con codigo: {}", codigo);
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlEscrito);
            stmt3 = conn.prepareStatement(sqlLibro);

            stmt1.setString(1, codigo);
            stmt1.executeUpdate();
            stmt2.setString(1, codigo);
            stmt2.executeUpdate();
            stmt3.setString(1, codigo);
            stmt3.executeUpdate();

            conn.commit();
            log.info("Eliminacion exitosa de Libro con codigo: {}", codigo);
        } catch (SQLException e) {
            log.error("Error al eliminar el Libro con codigo: {} - {}", codigo, e.getMessage());
            if(conn != null) conn.rollback();
            throw e;
        }
    }
}
