import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/mediateca?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final Logger log = LogManager.getLogger(ConexionBD.class);

    public static synchronized Connection conectar() throws SQLException {
        log.info("Intentando conectar a la base de datos: {}", URL);
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        log.info("Conexión exitosa a la base de datos: {}", URL);
        return conn;
    }

    public static void cerrar(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                log.info("ResultSet cerrado exitosamente");
            }
        } catch (SQLException e) {
            log.error("Error al cerrar a la base de datos (ResultSet): {}", e.getMessage());
        }
    }

    public static void cerrar(Statement st) {
        try {
            if (st != null) {
                st.close();
                log.info("Statement cerrado exitosamente");
            }
        } catch (SQLException e) {
            log.error("Error al cerrar a la base de datos (Statement): {}", e.getMessage());
        }
    }

    public static void cerrar(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                log.info("Conexión cerrada exitosamente");
            }
        } catch (SQLException e) {
            log.error("Error al cerrar a la base de datos (Connection): {}", e.getMessage());
        }
    }
}