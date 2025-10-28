import javax.swing.*;
import java.awt.*;

public class _DialogConsulta extends JDialog {
    public _DialogConsulta(JFrame parent) {
        super(parent, "Sistema de Mediateca", true); // true = modal
        setSize(1000, 555);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelConsulta();
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
