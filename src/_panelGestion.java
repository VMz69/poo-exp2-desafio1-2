import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class _panelGestion extends JPanel {
    private JLabel lblCodigo;
    private JComboBox<String> cboxTipoMaterial;
    private JTextField txtTitulo, txtEditorial, txtUnidades, txtPeriodicidad, txtFecha;
    private JTextField txtAutor, txtAnio, txtNumeroPaginas, txtIsbn;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RevistaDAO dao = new RevistaDAO();
    private LibroDAO libroDAO = new LibroDAO();
    private DvdDAO dvdDAO = new DvdDAO();
    private CdAudioDAO cdAudioDAO = new CdAudioDAO();
    private String codigoActual = "";
    private ArrayList<Libro> librosActuales = new ArrayList<>();
    private ArrayList<Revista> revistasActuales = new ArrayList<>();
    private ArrayList<Dvd> dvdsActuales = new ArrayList<>();
    private ArrayList<CdAudio> cdsActuales = new ArrayList<>();




    public _panelGestion() {
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

        lblCodigo = new JLabel("");
        formulario.add(new JLabel("Código de material seleccionado:"));
        formulario.add(lblCodigo);



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

        //Action listener de boton de borrar
        btnEliminar.addActionListener(e -> {
            String tipoMaterial = (String) cboxTipoMaterial.getSelectedItem();
            if (tipoMaterial == null) return;

            switch (tipoMaterial) {
                case "Revista":
                    eliminarRevista();
                    break;
                case "Libro":
                    eliminarLibro();
                    break;
                case "DVD":
                    eliminarDvd();
                    break;
                case "CD-Audio":
                    eliminarCd();
                    break;
            }
        });
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

            if (tabla.isEditing()) {
                tabla.getCellEditor().stopCellEditing();
            }

            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un material primero.");
                return;
            }
            Window window = SwingUtilities.getWindowAncestor(this);

            String tipoMaterial = (String) cboxTipoMaterial.getSelectedItem();
            if (tipoMaterial == null) return;

            switch (tipoMaterial) {
                case "Revista":
                    new _DialogRevista(window, revistasActuales.get(fila));
                    break;
                case "Libro":
                    new _DialogLibro(window, librosActuales.get(fila));
                    break;
                case "DVD":
                    new _DialogDvd(window, dvdsActuales.get(fila));
                    break;
                case "CD-Audio":
                    new _DialogCdAudio(window, cdsActuales.get(fila));
                    break;
            }
        });

        btnListar.addActionListener(e -> {
            String tipoMaterial = (String) cboxTipoMaterial.getSelectedItem();
            if (tipoMaterial == null) return;

            switch (tipoMaterial) {
                case "Revista":
                    listarRevistas();
                    break;
                case "Libro":
                    listarLibros();   // ← you’ll create this method
                    break;
                case "DVD":
                    listarDvds();     // ← and this one too
                    break;
                case "CD-Audio":
                    listarCDAudios(); // ← optional if you support CDs
                    break;
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                String tipoMaterial = (String) cboxTipoMaterial.getSelectedItem();
                if (tipoMaterial == null) return;

                switch (tipoMaterial) {
                    case "Revista":
                        Revista revistaSeleccionada = revistasActuales.get(fila);
                        lblCodigo.setText(revistaSeleccionada.getCodigo());
                        codigoActual = revistaSeleccionada.getCodigo();
                        break;
                    case "Libro":
                        Libro libroSeleccionado = librosActuales.get(fila);
                        lblCodigo.setText(libroSeleccionado.getCodigo());
                        codigoActual = libroSeleccionado.getCodigo();
                        break;
                    case "DVD":
                        Dvd dvdSeleccionado = dvdsActuales.get(fila);
                        lblCodigo.setText(dvdSeleccionado.getCodigo());
                        codigoActual = dvdSeleccionado.getCodigo();
                        break;
                    case "CD-Audio":
                        CdAudio cdSeleccionado = cdsActuales.get(fila);
                        lblCodigo.setText(cdSeleccionado.getCodigo());
                        codigoActual = cdSeleccionado.getCodigo();
                        break;
                }
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
            modeloTabla.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Editorial", "Unidades", "Periocidad", "Fecha"
            });
            revistasActuales = dao.listarRevistas(); //
            // 👈 guardas la lista completa

            for (Revista r : revistasActuales) {
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

    private void listarLibros() {
        try {
            modeloTabla.setRowCount(0);
            modeloTabla.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Editorial", "Unidades", "Autor", "Año de Publicación"
            });
            librosActuales = libroDAO.listarLibros(); // 👈 guardas la lista completa

            for (Libro l : librosActuales) {
                modeloTabla.addRow(new Object[]{
                        l.getCodigo(), l.getTitulo(), l.getEditorial(),
                        l.getUnidadesDisponibles(), l.getAutor(),
                        l.getAnioPublicacion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar libros: " + ex.getMessage());
        }
    }

    private void listarDvds() {
        try {
            modeloTabla.setRowCount(0);
            modeloTabla.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Director", "Unidades", "Genero", "Duracion (min)"
            });
            dvdsActuales = dvdDAO.listarDvds(); //

            for (Dvd d : dvdsActuales) {
                modeloTabla.addRow(new Object[]{
                        d.getCodigo(), d.getTitulo(), d.getDirector(),
                        d.getUnidadesDisponibles(), d.getGenero(),
                        d.getDuracion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar DVDs: " + ex.getMessage());
        }
    }

    private void listarCDAudios() {
        try {
            modeloTabla.setRowCount(0);
            modeloTabla.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Artista", "Unidades", "Género", "Duración (min)", "Canciones"
            });

            cdsActuales = cdAudioDAO.listarCds(); // tu DAO debe devolver List<CDaAudio>

            for (CdAudio cd : cdsActuales) {
                modeloTabla.addRow(new Object[]{
                        cd.getCodigo(),
                        cd.getTitulo(),
                        cd.getArtista(),
                        cd.getUnidadesDisponibles(),
                        cd.getGenero(),
                        cd.getDuracion(),
                        cd.getNumeroCanciones()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar CD-Audio: " + ex.getMessage());
        }
    }


    private void limpiarCampos() {
        if (lblCodigo != null) lblCodigo.setText("");
        if (txtTitulo != null) txtTitulo.setText("");
        if (txtEditorial != null) txtEditorial.setText("");
        if (txtUnidades != null) txtUnidades.setText("");
        if (txtAutor != null) txtAutor.setText("");
        if (txtAnio != null) txtAnio.setText("");
        if (txtNumeroPaginas != null) txtNumeroPaginas.setText("");
        if (txtIsbn != null) txtIsbn.setText("");
    }

    private void eliminarRevista() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una revista en la tabla para eliminar.");
            return;
        }
        try {
            dao.eliminarRevista(codigoActual);
            JOptionPane.showMessageDialog(this, "Revista eliminada correctamente.");
            limpiarCampos();
            listarRevistas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar revista: " + ex.getMessage());
        }
    }

    private void eliminarLibro() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro en la tabla para eliminar.");
            return;
        }
        try {
            libroDAO.eliminarLibro(codigoActual);
            JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
            limpiarCampos();
            listarLibros();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar libro: " + ex.getMessage());
        }
    }

    private void eliminarDvd() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un DVD en la tabla para eliminar.");
            return;
        }
        try {
            dvdDAO.eliminarDvd(codigoActual);
            JOptionPane.showMessageDialog(this, "DVD eliminado correctamente.");
            limpiarCampos();
            listarDvds();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar DVD: " + ex.getMessage());
        }
    }

    private void eliminarCd() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un CD Audio en la tabla para eliminar.");
            return;
        }
        try {
            cdAudioDAO.eliminarCd(codigoActual);
            JOptionPane.showMessageDialog(this, "CD Audio eliminado correctamente.");
            limpiarCampos();
            listarCDAudios();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar CD Audio: " + ex.getMessage());
        }
    }

}
