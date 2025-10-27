import javax.swing.*;
import java.awt.*;

public class _DialogDvd extends JDialog {
    public _DialogDvd(JFrame parent) {
        super(parent, "Sistema de Mediateca – DVD", true);
        setSize(650, 575);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelDvd();  // Utiliza tu panel de DVD
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}

