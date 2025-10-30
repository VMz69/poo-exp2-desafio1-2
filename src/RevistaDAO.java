import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class RevistaDAO {
    private static final Logger log = LogManager.getLogger(RevistaDAO.class);

    public void insertarRevista(Revista revista) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlEscrito = "INSERT INTO material_escrito (codigo, editorial, unidades_disponibles) VALUES (?, ?, ?)";
        String sqlRevista = "INSERT INTO revista (codigo, periodicidad, fecha_publicacion) VALUES (?, ?, ?)";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);
            log.info("Iniciando insercion de Revista con codigo: {}", revista.getCodigo());

            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlEscrito);
            stmt3 = conn.prepareStatement(sqlRevista);

            stmt1.setString(1, revista.getCodigo());
            stmt1.setString(2, revista.getTitulo());
            stmt1.executeUpdate();

            stmt2.setString(1, revista.getCodigo());
            stmt2.setString(2, revista.getEditorial());
            stmt2.setInt(3, revista.getUnidadesDisponibles());
            stmt2.executeUpdate();

            stmt3.setString(1, revista.getCodigo());
            stmt3.setString(2, revista.getPeriodicidad());
            stmt3.setDate(3, Date.valueOf(revista.getFechaPublicacion()));
            stmt3.executeUpdate();

            conn.commit();
            log.info("Se ha insertado correctamente la Revista con codigo: {}", revista.getCodigo());
        } catch (SQLException e) {
            log.error("Error al insertar la Revista con codigo: {} - {}", revista.getCodigo(), e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(conn);
        }
    }

    public ArrayList<Revista> listarRevistas() throws SQLException {
        ArrayList<Revista> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT m.codigo, m.titulo, me.editorial, me.unidades_disponibles,\n" +
                "       r.periodicidad, r.fecha_publicacion\n" +
                "FROM revista r\n" +
                "JOIN material_escrito me ON r.codigo = me.codigo\n" +
                "JOIN material m ON me.codigo = m.codigo";

        try {
            conn = ConexionBD.conectar();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            log.info("Listando todos los registros de Revista");
            while (rs.next()) {
                Revista revista = new Revista(
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getString("editorial"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("periodicidad"),
                        rs.getDate("fecha_publicacion").toLocalDate()
                );
                lista.add(revista);
            }
            log.info("Listado {} registros de Revista", lista.size());
        } catch (SQLException e) {
            log.error("Error al listar los registros de Revista: {}", e.getMessage());
            throw e;
        } finally {
            ConexionBD.cerrar(rs);
            ConexionBD.cerrar(stmt);
            ConexionBD.cerrar(conn);
        }
        return lista;
    }

    public void actualizarRevista(Revista revista) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;

        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlEscrito = "UPDATE material_escrito SET editorial = ?, unidades_disponibles = ? WHERE codigo = ?";
        String sqlRevista = "UPDATE revista SET periodicidad = ?, fecha_publicacion = ? WHERE codigo = ?";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando actualizacion de Revista con codigo: {}", revista.getCodigo());
            stmt1 = conn.prepareStatement(sqlMaterial);
            stmt2 = conn.prepareStatement(sqlEscrito);
            stmt3 = conn.prepareStatement(sqlRevista);

            stmt1.setString(1, revista.getTitulo());
            stmt1.setString(2, revista.getCodigo());
            stmt1.executeUpdate();

            stmt2.setString(1, revista.getEditorial());
            stmt2.setInt(2, revista.getUnidadesDisponibles());
            stmt2.setString(3, revista.getCodigo());
            stmt2.executeUpdate();

            stmt3.setString(1, revista.getPeriodicidad());
            stmt3.setDate(2, Date.valueOf(revista.getFechaPublicacion()));
            stmt3.setString(3, revista.getCodigo());
            stmt3.executeUpdate();

            conn.commit();
            log.info("Actualizacion exitosa de Revista con codigo: {}", revista.getCodigo());
        } catch (SQLException e) {
            log.error("Error al actualizar Revista con codigo: {} - {}", revista.getCodigo(), e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(conn);
        }
    }

    public void eliminarRevista(String codigo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt1 = null;

        String sqlMaterial = "DELETE FROM material WHERE codigo = ?";
        String sqlEscrito = "DELETE FROM material_escrito WHERE codigo = ?";
        String sqlRevista = "DELETE FROM revista WHERE codigo = ?";

        try {
            conn = ConexionBD.conectar();
            conn.setAutoCommit(false);

            log.info("Iniciando eliminacion de Revista con codigo: {}", codigo);
            stmt3 = conn.prepareStatement(sqlRevista);
            stmt2 = conn.prepareStatement(sqlEscrito);
            stmt1 = conn.prepareStatement(sqlMaterial);

            stmt3.setString(1, codigo); stmt3.executeUpdate();
            stmt2.setString(1, codigo); stmt2.executeUpdate();
            stmt1.setString(1, codigo); stmt1.executeUpdate();

            conn.commit();
            log.info("Eliminacion exitosa de Revista con codigo: {}", codigo);
        } catch (SQLException e) {
            log.error("Error al eliminar Revista con codigo: {} - {}", codigo, e.getMessage());
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            ConexionBD.cerrar(stmt1);
            ConexionBD.cerrar(stmt2);
            ConexionBD.cerrar(stmt3);
            ConexionBD.cerrar(conn);
        }
    }
}