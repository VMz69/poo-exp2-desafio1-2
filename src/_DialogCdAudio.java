import javax.swing.*;
import java.awt.*;

public class _DialogCdAudio extends JDialog {
    public _DialogCdAudio(Window parent, CdAudio cdAudio) {
        super(parent, "Sistema de Mediateca - Libro", ModalityType.APPLICATION_MODAL);
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new _panelCdAudio(cdAudio); // con su respectivo panel
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Modo crear (sin datos)
    public _DialogCdAudio(Window parent) {
        this(parent, null);
    }
}