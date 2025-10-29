import javax.swing.*;
import java.awt.*;

public class _DialogLibro extends JDialog {

    public _DialogLibro(Window parent, Libro libro) {
        super(parent, "Sistema de Mediateca - Libro", ModalityType.APPLICATION_MODAL);
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelLibro(libro);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public _DialogLibro(Window parent) {
        this(parent, null);
    }
}