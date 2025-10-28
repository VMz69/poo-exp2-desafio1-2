import javax.swing.*;
import java.awt.*;

public class _DialogCdAudio extends JDialog {
    public _DialogCdAudio(JFrame parent) {
        super(parent, "Sistema de Mediateca – CD", true);
        setSize(650,675);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelCd();
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
