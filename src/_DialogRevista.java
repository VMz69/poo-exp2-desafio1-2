import javax.swing.*;
import java.awt.*;

public class _DialogRevista extends JDialog {
    public _DialogRevista(Window parent, Revista revista) {
        super(parent, "Sistema de Mediateca - Revista", ModalityType.APPLICATION_MODAL);
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelRevista(revista);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Para modo crear (sin datos)
    public _DialogRevista(Window parent) {
        this(parent, null);
    }
}
