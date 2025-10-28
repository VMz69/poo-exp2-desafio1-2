import javax.swing.*;
import java.awt.*;

public class _DialogLibro extends JDialog {
    public _DialogLibro(JFrame parent, Libro libro) {
        super(parent, "Sistema de Mediateca - Libro", true);
        setSize(650, 575);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelLibro2(libro); // 👈 you’ll make this panel next
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Modo crear (sin datos)
    public _DialogLibro(JFrame parent) {
        this(parent, null);
    }
}
