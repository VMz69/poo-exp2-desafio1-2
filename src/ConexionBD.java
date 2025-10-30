import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/mediateca";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final Logger log = LogManager.getLogger(ConexionBD.class);

    public static Connection conectar() throws SQLException {
        log.info("Intentando conectar a la base de datos: {}", URL);
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            log.info("Conexión exitosa a la base de datos: {}", URL);
            return conn;
        } catch (SQLException e) {
            log.error("Error al conectar a la base de datos {}: {}", URL, e.getMessage());
            throw e;        }
    }
}