import javax.swing.*;
import java.awt.*;

public class DialogBuscar extends JDialog {
    public DialogBuscar(JFrame parent) {
        super(parent, "Sistema de Mediateca", true); // true = modal
        setSize(1000, 555);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new panelBuscar();
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
