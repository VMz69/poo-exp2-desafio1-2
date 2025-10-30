import javax.swing.*;
import java.awt.*;

public class DialogDvd extends JDialog {
    public DialogDvd(Window parent, Dvd dvd) {
        super(parent, "Sistema de Mediateca - Libro", ModalityType.APPLICATION_MODAL);
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new panelDvd(dvd); // con su respectivo panel
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Modo crear (sin datos)
    public DialogDvd(Window parent) {
        this(parent, null);
    }
}
