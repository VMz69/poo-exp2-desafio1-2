import javax.swing.*;
import java.awt.*;

public class _DialogGestion extends JDialog {
    public _DialogGestion(JFrame parent) {
        super(parent, "Sistema de Mediateca", true); // Modal
        setSize(750, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // === Aplicar diseño consistente con el resto de la app ===
        getContentPane().setBackground(new Color(240, 240, 240)); // Fondo claro
        setLayout(new BorderLayout());


        JLabel lblTitulo = new JLabel("GESTIÓN DE MATERIALES", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));


        // --- Panel principal (contenido del diálogo) ---
        JPanel panel = new _panelGestion();
        panel.setBackground(new Color(245, 245, 245)); // Fondo neutro dentro del panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panel, BorderLayout.CENTER);

        // --- Borde sutil para que se vea más profesional ---
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220)));

        setVisible(true);
    }
}
