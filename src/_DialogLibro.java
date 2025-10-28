import javax.swing.*;
import java.awt.*;

public class _DialogLibro extends JDialog {
    public _DialogLibro(JFrame parent) {
        super(parent, "Sistema de Mediateca – Libro", true);
        setSize(650, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelLibro();
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
