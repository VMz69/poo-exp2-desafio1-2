import javax.swing.*;
import java.awt.*;

public class panelConsulta extends JPanel {
    public panelConsulta() {
        // --- Configuración general del panel principal ---
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Fondo claro principal

        // ============================================================
        // ENCABEZADO SUPERIOR
        // ============================================================
        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.setBackground(new Color(45, 48, 80)); // Azul oscuro sobrio
        panelNorth.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); // Márgenes internos

        JLabel lblTitulo = new JLabel("CONSULTAR Y LISTAR MATERIALES DE MEDIATECA", JLabel.CENTER);
        lblTitulo.setForeground(new Color(240, 240, 240)); // Texto claro
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 17)); // Arial negrita, elegante y legible
        panelNorth.add(lblTitulo, BorderLayout.CENTER);

        add(panelNorth, BorderLayout.NORTH);

        // ============================================================
        // PESTAÑAS CENTRALES
        // ============================================================
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.PLAIN, 13));
        tabs.setBackground(new Color(240, 240, 240));
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Personalización sobria del área de pestañas
        UIManager.put("TabbedPane.selected", new Color(100, 130, 255));
        UIManager.put("TabbedPane.unselectedBackground", new Color(180, 180, 220));
        UIManager.put("TabbedPane.contentAreaColor", new Color(240, 240, 240));

        // Pestañas con sus paneles respectivos
        tabs.addTab("Libros", new tabLibro());
        tabs.addTab("Revistas", new tabRevista());
        tabs.addTab("CDs de Audio", new tabCdAudio());
        tabs.addTab("DVDs", new tabDVD());

        add(tabs, BorderLayout.CENTER);

        // ============================================================
        // PANEL INFERIOR (BOTÓN SALIR)
        // ============================================================
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelSur.setBackground(new Color(240, 240, 240)); // Fondo claro para coherencia visual

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(255, 107, 107)); // Rojo suave de la paleta
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15)); // Padding interno
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover sencillo
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalir.setBackground(new Color(230, 85, 85));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalir.setBackground(new Color(255, 107, 107));
            }
        });

        // Acción del botón: cierra la ventana contenedora
        btnSalir.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });

        panelSur.add(btnSalir);
        add(panelSur, BorderLayout.SOUTH);
    }
}
