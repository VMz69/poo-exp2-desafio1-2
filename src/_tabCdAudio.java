import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class _tabCdAudio extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private CdAudioDAO dao = new CdAudioDAO();

    public _tabCdAudio() {
        setLayout(new BorderLayout());

        // Panel superior (North) con label y botones
        JPanel panelNorth = new JPanel(new BorderLayout());

        JPanel panelBotones = new JPanel();
        JButton btnListarTodos = new JButton("Ver / Listar todos los CdAudio");
        JButton btnListarDisponibles = new JButton("Listar solo CdAudio disponibles");
        panelBotones.add(btnListarTodos);
        panelBotones.add(btnListarDisponibles);

        panelNorth.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // Tabla en el centro
        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Duración", "Unidades", "Género", "Artista", "Canciones"
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
        btnListarTodos.addActionListener(e -> listarCds());
        btnListarDisponibles.addActionListener(e -> listarDisponibles());
        btnSalir.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });
    }

    private void listarCds() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<CdAudio> cds = dao.listarCds();
            for (CdAudio cd : cds) {
                modeloTabla.addRow(new Object[]{
                        cd.getCodigo(), cd.getTitulo(), cd.getDuracion(),
                        cd.getUnidadesDisponibles(), cd.getGenero(),
                        cd.getArtista(), cd.getNumeroCanciones()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar CDs: " + ex.getMessage());
        }
    }

    private void listarDisponibles() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<CdAudio> cds = dao.listarCds();
            for (CdAudio cd : cds) {
                if (cd.getUnidadesDisponibles() > 0) {
                    modeloTabla.addRow(new Object[]{
                            cd.getCodigo(), cd.getTitulo(), cd.getDuracion(),
                            cd.getUnidadesDisponibles(), cd.getGenero(),
                            cd.getArtista(), cd.getNumeroCanciones()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar CDs disponibles: " + ex.getMessage());
        }
    }

}
