import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class _tabDVD extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private DvdDAO dao = new DvdDAO();

    public _tabDVD() {
        setLayout(new BorderLayout());

        // Panel superior (North) con label y botones
        JPanel panelNorth = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel();
        JButton btnListarTodos = new JButton("Ver / Listar todos los DVD");
        JButton btnListarDisponibles = new JButton("Listar solo DVD disponibles");
        panelBotones.add(btnListarTodos);
        panelBotones.add(btnListarDisponibles);

        panelNorth.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // Tabla en el centro
        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Duración", "Unidades", "Género", "Director"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botón salir en el sur
        JButton btnSalir = new JButton("Salir");
        JPanel panelSur = new JPanel();
        panelSur.add(btnSalir);
        add(panelSur, BorderLayout.SOUTH);

        // Listeners
        btnListarTodos.addActionListener(e -> listarDvds());
        btnListarDisponibles.addActionListener(e -> listarDisponibles());
        btnSalir.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });
    }

    private void listarDvds() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<Dvd> dvds = dao.listarDvds();
            for (Dvd dvd : dvds) {
                modeloTabla.addRow(new Object[]{
                        dvd.getCodigo(), dvd.getTitulo(), dvd.getDuracion(),
                        dvd.getUnidadesDisponibles(), dvd.getGenero(), dvd.getDirector()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar DVDs: " + ex.getMessage());
        }
    }

    private void listarDisponibles() {
        try {
            modeloTabla.setRowCount(0);

            ArrayList<Dvd> dvds = dao.listarDvds();

            for (Dvd dvd : dvds) {
                if (dvd.getUnidadesDisponibles() > 0) {
                    modeloTabla.addRow(new Object[]{
                            dvd.getCodigo(), dvd.getTitulo(), dvd.getDuracion(),
                            dvd.getUnidadesDisponibles(), dvd.getGenero(), dvd.getDirector()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar DVDs disponibles: " + ex.getMessage());
        }
    }

}
