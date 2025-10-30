import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class tabLibro extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private LibroDAO dao = new LibroDAO();

    public tabLibro() {
        // --- Configuración general del panel ---
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Fondo claro general

        // ============================================================
        // PANEL SUPERIOR CON BOTONES
        // ============================================================
        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.setBackground(new Color(240, 240, 240)); // Mantiene coherencia visual
        panelNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel que contiene los botones alineados horizontalmente
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(new Color(240, 240, 240));

        // --- Botón "Listar todos los Libros" ---
        JButton btnListarTodos = new JButton("Ver / Listar todos los Libros");
        btnListarTodos.setFont(new Font("Arial", Font.BOLD, 13));
        btnListarTodos.setForeground(Color.WHITE);
        btnListarTodos.setBackground(new Color(45, 48, 80));
        btnListarTodos.setFocusPainted(false);
        btnListarTodos.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnListarTodos.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btnListarTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnListarTodos.setBackground(new Color(60, 83, 166));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnListarTodos.setBackground(new Color(45, 48, 80));
            }
        });

        // --- Botón "Listar disponibles" ---
        JButton btnListarDisponibles = new JButton("Listar solo Libros disponibles");
        btnListarDisponibles.setFont(new Font("Arial", Font.BOLD, 13));
        btnListarDisponibles.setForeground(Color.WHITE);
        btnListarDisponibles.setBackground(new Color(80, 150, 80)); // Verde suave de la paleta
        btnListarDisponibles.setFocusPainted(false);
        btnListarDisponibles.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnListarDisponibles.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btnListarDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnListarDisponibles.setBackground(new Color(65, 130, 65));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnListarDisponibles.setBackground(new Color(80, 150, 80));
            }
        });

        // Agregar botones al panel
        panelBotones.add(btnListarTodos);
        panelBotones.add(btnListarDisponibles);

        // Agregar al contenedor norte
        panelNorth.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // ============================================================
        // TABLA CENTRAL
        // ============================================================
        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Editorial", "Unidades", "Autor", "Páginas", "ISBN", "Año"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace todas las celdas no editables
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(180, 180, 220));
        tabla.getTableHeader().setForeground(new Color(45, 48, 80));
        tabla.setRowHeight(22);
        tabla.setGridColor(new Color(200, 200, 200));
        tabla.setSelectionBackground(new Color(100, 130, 255));
        tabla.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // ============================================================
        // EVENTOS DE BOTONES
        // ============================================================
        btnListarTodos.addActionListener(e -> listarLibros());
        btnListarDisponibles.addActionListener(e -> listarDisponibles());
    }

    // ------------------------------------------------------------
    // MÉTODOS DE LISTADO
    // ------------------------------------------------------------

    private void listarLibros() {
        try {
            modeloTabla.setRowCount(0); // Limpia la tabla antes de listar
            ArrayList<Libro> libros = dao.listarLibros();
            for (Libro libro : libros) {
                modeloTabla.addRow(new Object[]{
                        libro.getCodigo(), libro.getTitulo(), libro.getEditorial(),
                        libro.getUnidadesDisponibles(), libro.getAutor(),
                        libro.getNumeroPaginas(), libro.getIsbn(), libro.getAnioPublicacion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar libros: " + ex.getMessage());
        }
    }

    private void listarDisponibles() {
        try {
            modeloTabla.setRowCount(0); // Limpia la tabla antes de listar
            ArrayList<Libro> libros = dao.listarLibros();
            for (Libro libro : libros) {
                if (libro.getUnidadesDisponibles() > 0) {
                    modeloTabla.addRow(new Object[]{
                            libro.getCodigo(), libro.getTitulo(), libro.getEditorial(),
                            libro.getUnidadesDisponibles(), libro.getAutor(),
                            libro.getNumeroPaginas(), libro.getIsbn(), libro.getAnioPublicacion()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar libros disponibles: " + ex.getMessage());
        }
    }
}
