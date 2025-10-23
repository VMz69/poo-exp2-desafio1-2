import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class _panelGestion_prueba extends JPanel {
    private JComboBox<String> cboxTipoMaterial;
    private JTextField txtTitulo, txtEditorial, txtUnidades, txtPeriodicidad, txtFecha;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RevistaDAO dao = new RevistaDAO();
    private String codigoActual = "";

    public _panelGestion_prueba() {
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(6, 2));


        JComboBox<String> cboxTipoMaterial;
        cboxTipoMaterial = new JComboBox<>(new String[]{
                "Revista", "Libro", "CD-Audio", "DVD"
        });
        formulario.add(new JLabel("Seleccione un Tipo de Material:"));
        formulario.add(cboxTipoMaterial);


        txtTitulo = new JTextField();
        formulario.add(new JLabel("Ingrese el titulo del material que desea buscar:"));
        formulario.add(txtTitulo);





        JButton btnListar = new JButton("(''BUSQUEDA'')");
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(formulario);
        northPanel.add(btnListar);

        add(northPanel, BorderLayout.NORTH);

//        El resto de botones en SOUTH
        JPanel botones = new JPanel();

        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnSalir = new JButton("Salir");
        botones.add(btnEliminar);
        btnEliminar.addActionListener(e -> eliminarRevista());
        botones.add(btnSalir);
        btnSalir.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });

        botones.add(btnModificar);
        add(botones, BorderLayout.SOUTH);






        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Editorial", "Unidades", "Periodicidad", "Fecha"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnModificar.addActionListener(e -> {
            String tipoMaterial = (String) cboxTipoMaterial.getSelectedItem();
            if (tipoMaterial != null) {
                switch (tipoMaterial) {
                    case "Revista":
                        new _DialogRevista(null);
                        break;
                    case "Libro":
                        //new DialogLibro(parentFrame);
                        break;
                    case "DVD":
                        //new DialogDVD(parentFrame);
                        break;
                }
            }
        });
        btnListar.addActionListener(e -> listarRevistas());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
//                codigoActual = tabla.getValueAt(fila, 0).toString();
//                lblCodigo.setText(codigoActual);
//                txtTitulo.setText(tabla.getValueAt(fila, 1).toString());
//                txtEditorial.setText(tabla.getValueAt(fila, 2).toString());
//                txtUnidades.setText(tabla.getValueAt(fila, 3).toString());
//                txtPeriodicidad.setText(tabla.getValueAt(fila, 4).toString());
//                txtFecha.setText(tabla.getValueAt(fila, 5).toString());
            }
        });
    }

    private void modificarRevista() {


// Entonces puedes hacer directamente:
        String tipoMaterial = (String) cboxTipoMaterial.getSelectedItem();
        if (tipoMaterial != null) {
            switch (tipoMaterial) {
                case "Revista":
                    new _DialogRevista(null);
                    break;
                case "Libro":
                    //new DialogLibro(parentFrame);
                    break;
                case "DVD":
                    //new DialogDVD(parentFrame);
                    break;
            }
        }




//       ***********************************LA FUNCION DE ACTUALIZAR DEBE TRASLADARSE AL JDIALOG CORRESPONDIENTE************************************
        //        try {
//            Revista revista = new Revista(
//                    codigoActual,
//                    txtTitulo.getText(),
//                    txtEditorial.getText(),
//                    Integer.parseInt(txtUnidades.getText()),
//                    txtPeriodicidad.getText(),
//                    LocalDate.parse(txtFecha.getText())
//            );
//            dao.actualizarRevista(revista);
//            JOptionPane.showMessageDialog(this, "Revista modificada correctamente.");
//            listarRevistas();
//            limpiarCampos();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error al modificar revista: " + ex.getMessage());
//        }
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

    private void limpiarCampos() {
        codigoActual = "";
        txtTitulo.setText(""); txtEditorial.setText(""); txtUnidades.setText("");
        txtPeriodicidad.setText(""); txtFecha.setText("");
    }

    private void eliminarRevista() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una revista en la tabla para eliminar.");
            return;
        }
        try {
            dao.eliminarRevista(codigoActual);
            JOptionPane.showMessageDialog(this, "Revista eliminada correctamente.");
            listarRevistas();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar revista: " + ex.getMessage());
        }
    }
}
