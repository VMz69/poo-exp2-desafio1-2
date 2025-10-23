import javax.swing.*;
import java.awt.*;

public class _DialogRevista extends JDialog {
    public _DialogRevista(JFrame parent, Revista revista) {
        super(parent, "Sistema de Mediateca", true);
        setSize(650, 575);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelRevista2(revista);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Para modo crear (sin datos)
    public _DialogRevista(JFrame parent) {
        this(parent, null);
    }
}

