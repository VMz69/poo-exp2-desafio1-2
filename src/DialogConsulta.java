import javax.swing.*;
import java.awt.*;

public class DialogConsulta extends JDialog {
    public DialogConsulta(JFrame parent) {
        super(parent, "Sistema de Mediateca", true); // true = modal
        setSize(1000, 555);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new panelConsulta();
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
