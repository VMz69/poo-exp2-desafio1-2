import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;

public class MaterialDAO{
    private static final Logger log =  LogManager.getLogger(MaterialDAO.class);



    public ArrayList<Material> listarMateriales() throws SQLException {
        ArrayList<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM material";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            log.info("Listando todos los registros de Material");
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
            log.info("Listando {} registros de Libro", lista.size());
        } catch (SQLException e){
            log.error("Error al listar los registros de Libro: {}", e.getMessage());
            throw e;
        }
        return lista;
    }


    public ArrayList<Material> buscarMateriales(String textoBusqueda) throws SQLException {
        ArrayList<Material> lista = new ArrayList<>();
        String sql = "SELECT * FROM material WHERE codigo LIKE ? OR titulo LIKE ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String criterio = "%" + textoBusqueda + "%";
            pstmt.setString(1, criterio);
            pstmt.setString(2, criterio);

            try (ResultSet rs = pstmt.executeQuery()) {
                log.info("Buscando registros de Material con texto: {}", textoBusqueda);
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
            }

        } catch (SQLException e) {
            log.error("Error al buscar los registros de Material: {}", e.getMessage());
            throw e;
        }
        return lista;
    }



}