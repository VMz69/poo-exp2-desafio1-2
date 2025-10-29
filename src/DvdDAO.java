import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;

public class DvdDAO {
    private static final Logger log = LogManager.getLogger(DvdDAO.class);

    public void insertarDvd(Dvd dvd) throws SQLException {
        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlAudiovisual = "INSERT INTO material_audiovisual (codigo, duracion, unidades_disponibles, genero) VALUES (?, ?, ?, ?)";
        String sqlDvd = "INSERT INTO dvd (codigo, director) VALUES (?, ?)";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            log.info("Iniciando insercion de DVD con codigo: {}", dvd.getCodigo());
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlAudiovisual);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlDvd)) {

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
                conn.rollback();
                throw e;
            }
        }
    }

    public ArrayList<Dvd> listarDvds() throws SQLException {
        ArrayList<Dvd> lista = new ArrayList<>();
        String sql = "SELECT m.codigo, m.titulo, ma.duracion, ma.unidades_disponibles, ma.genero,\n" +
                        "       d.director\n" +
                        "FROM dvd d\n" +
                        "JOIN material_audiovisual ma ON d.codigo = ma.codigo\n" +
                        "JOIN material m ON ma.codigo = m.codigo";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
            log.info("Listando {} registros de DVD", lista.size());
        } catch (SQLException e) {
            log.error("Error al listar los registros de DVD: {}", e.getMessage());
            throw e;
        }
        return lista;
    }

    public void actualizarDvd(Dvd dvd) throws SQLException {
        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlAudiovisual = "UPDATE material_audiovisual SET duracion = ?, unidades_disponibles = ?, genero = ? WHERE codigo = ?";
        String sqlDvd = "UPDATE dvd SET director = ? WHERE codigo = ?";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            log.info("Iniciando actualizacion de DVD con codigo: {}", dvd.getCodigo());
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlAudiovisual);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlDvd)) {

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
                conn.rollback();
                throw e;
            }
        }
    }

    public void eliminarDvd(String codigo) throws SQLException {
        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            log.info("Iniciando eliminacion de DVD con codigo: {}", codigo);
            try (PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM dvd WHERE codigo = ?");
                 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM material_audiovisual WHERE codigo = ?");
                 PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM material WHERE codigo = ?")) {

                stmt3.setString(1, codigo); stmt3.executeUpdate();
                stmt2.setString(1, codigo); stmt2.executeUpdate();
                stmt1.setString(1, codigo); stmt1.executeUpdate();

                conn.commit();
                log.info("Eliminacion exitosa de DVD con codigo: {}", codigo);
            } catch (SQLException e) {
                log.error("Error al eliminar el DVD con codigo: {} - {}", codigo, e.getMessage());
                conn.rollback();
                throw e;
            }
        }
    }
}