import javax.swing.*;
import java.awt.*;


public class _DialogGestion extends JDialog {
    public _DialogGestion(JFrame parent) {
        super(parent, "Sistema de Mediateca", true); // true = modal
        setSize(750, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelGestion();
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }



}
