import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;

public class CdAudioDAO {
    private static final Logger log = LogManager.getLogger(CdAudioDAO.class);

    public void insertarCd(CdAudio cd) throws SQLException {
        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlAudiovisual = "INSERT INTO material_audiovisual (codigo, duracion, unidades_disponibles, genero) VALUES (?, ?, ?, ?)";
        String sqlCd = "INSERT INTO cd_audio (codigo, artista, numero_canciones) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            log.info("Iniciando insercion de CD de Audio con codigo: {}", cd.getCodigo());
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlAudiovisual);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlCd)) {

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
                conn.rollback();
                throw e;
            }
        }
    }

    public ArrayList<CdAudio> listarCds() throws SQLException {
        ArrayList<CdAudio> lista = new ArrayList<>();
        String sql = "SELECT m.codigo, m.titulo, ma.duracion, ma.unidades_disponibles, ma.genero,\n" +
                        "       c.artista, c.numero_canciones\n" +
                        "FROM cd_audio c\n" +
                        "JOIN material_audiovisual ma ON c.codigo = ma.codigo\n" +
                        "JOIN material m ON ma.codigo = m.codigo";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
            log.info("Listando {} registros de CD de Audio", lista.size());
        }catch (SQLException e){
            log.error("Error al listar los registros de CD de Audio: {}", e.getMessage());
        }

        return lista;
    }

    public void actualizarCd(CdAudio cd) throws SQLException {
        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlAudiovisual = "UPDATE material_audiovisual SET duracion = ?, unidades_disponibles = ?, genero = ? WHERE codigo = ?";
        String sqlCd = "UPDATE cd_audio SET artista = ?, numero_canciones = ? WHERE codigo = ?";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            log.info("Iniciando actualizacion de CD de Audio con codigo: {}", cd.getCodigo());
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlAudiovisual);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlCd)) {

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
                log.info("Actualizacion exitosa de CD de Audio con codigo: {}", cd.getCodigo());
            } catch (SQLException e) {
                log.error("Error al actualizar el CD de Audio con codigo: {} - {}",  cd.getCodigo(), e.getMessage());
                conn.rollback();
                throw e;
            }
        }
    }

    public void eliminarCd(String codigo) throws SQLException {
        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            log.info("Iniciando eliminacion de CD de Audio con codigo: {}", codigo);
            try (PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM cd_audio WHERE codigo = ?");
                 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM material_audiovisual WHERE codigo = ?");
                 PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM material WHERE codigo = ?")) {

                stmt3.setString(1, codigo); stmt3.executeUpdate();
                stmt2.setString(1, codigo); stmt2.executeUpdate();
                stmt1.setString(1, codigo); stmt1.executeUpdate();

                conn.commit();
                log.info("Eliminacion exitosa de CD de Audio con codigo: {}", codigo);
            } catch (SQLException e) {
                log.error("Error al eliminar el CD de Audio con codigo: {} - {}", codigo, e.getMessage());
                conn.rollback();
                throw e;
            }
        }
    }
}