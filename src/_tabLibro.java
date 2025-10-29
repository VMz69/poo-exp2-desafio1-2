import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class _tabLibro extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private LibroDAO dao = new LibroDAO();

    public _tabLibro() {
        setLayout(new BorderLayout());

        // Panel superior (North) con label y botones
        JPanel panelNorth = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel();
        JButton btnListarTodos = new JButton("Ver / Listar todos los Libros");
        JButton btnListarDisponibles = new JButton("Listar solo Libros disponibles");
        panelBotones.add(btnListarTodos);
        panelBotones.add(btnListarDisponibles);

        panelNorth.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // Tabla en el centro
        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Editorial", "Unidades", "Autor", "Páginas", "ISBN", "Año"
        }, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botón salir en el sur
        JButton btnSalir = new JButton("Salir");
        JPanel panelSur = new JPanel();
        panelSur.add(btnSalir);
        add(panelSur, BorderLayout.SOUTH);

        // Listeners
        btnListarTodos.addActionListener(e -> listarLibros());
        btnListarDisponibles.addActionListener(e -> listarDisponibles());
        btnSalir.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });
    }

    private void listarLibros() {
        try {
            modeloTabla.setRowCount(0);
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
            modeloTabla.setRowCount(0);
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
