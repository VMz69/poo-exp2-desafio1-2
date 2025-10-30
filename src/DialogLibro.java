import javax.swing.*;
import java.awt.*;

public class DialogLibro extends JDialog {

    public DialogLibro(Window parent, Libro libro) {
        super(parent, "Sistema de Mediateca - Libro", ModalityType.APPLICATION_MODAL);
        setSize(700, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new panelLibro(libro);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public DialogLibro(Window parent) {
        this(parent, null);
    }
}