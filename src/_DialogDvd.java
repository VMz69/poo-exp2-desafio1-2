import javax.swing.*;
import java.awt.*;

public class _DialogDvd extends JDialog {
    public _DialogDvd(Window parent, Dvd dvd) {
        super(parent, "Sistema de Mediateca - Libro", ModalityType.APPLICATION_MODAL);
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelDvd(dvd); // con su respectivo panel
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Modo crear (sin datos)
    public _DialogDvd(Window parent) {
        this(parent, null);
    }
}
