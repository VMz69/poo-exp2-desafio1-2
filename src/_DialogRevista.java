import javax.swing.*;
import java.awt.*;

public class _DialogRevista extends JDialog {
    public _DialogRevista(JFrame parent) {
        super(parent, "Sistema de Mediateca", true); // true = modal
        setSize(650, 575);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelRevista();
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
