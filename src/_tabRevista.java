import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class _tabRevista extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RevistaDAO dao = new RevistaDAO();

    public _tabRevista() {
        setLayout(new BorderLayout());

        // Panel superior (North) con label y botones
        JPanel panelNorth = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel();
        JButton btnListarTodos = new JButton("Ver / Listar todos las Revistas");
        JButton btnListarDisponibles = new JButton("Listar solo Revistas disponibles");
        panelBotones.add(btnListarTodos);
        panelBotones.add(btnListarDisponibles);

        panelNorth.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // Tabla en el centro
        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Editorial", "Unidades", "Periodicidad", "Fecha"
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
        btnListarTodos.addActionListener(e -> listarRevistas());
        btnListarDisponibles.addActionListener(e -> listarDisponibles());
        btnSalir.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });
    }

    private void listarRevistas() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<Revista> revistas = dao.listarRevistas();
            for (Revista r : revistas) {
                modeloTabla.addRow(new Object[]{
                        r.getCodigo(), r.getTitulo(), r.getEditorial(),
                        r.getUnidadesDisponibles(), r.getPeriodicidad(),
                        r.getFechaPublicacion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar revistas: " + ex.getMessage());
        }
    }

    private void listarDisponibles() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<Revista> revistas = dao.listarRevistas();
            for (Revista r : revistas) {
                if (r.getUnidadesDisponibles() > 0) {
                    modeloTabla.addRow(new Object[]{
                            r.getCodigo(), r.getTitulo(), r.getEditorial(),
                            r.getUnidadesDisponibles(), r.getPeriodicidad(),
                            r.getFechaPublicacion()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar revistas disponibles: " + ex.getMessage());
        }
    }
}
