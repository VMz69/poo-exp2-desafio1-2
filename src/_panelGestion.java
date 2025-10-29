import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel básico para gestionar materiales de mediateca.
 */
public class _panelGestion extends JPanel {

    // Colores básicos según paleta
    private final Color COLOR_FONDO = new Color(45, 48, 80);
    private final Color COLOR_TEXTO = new Color(240, 240, 240);
    private final Color COLOR_BOTON_ROJO = new Color(255, 107, 107);
    private final Color COLOR_BOTON_AZUL = new Color(100, 130, 255);

    private JLabel lblCodigo;
    private JComboBox<String> cboxTipoMaterial;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // DAOs (sin cambios)
    private RevistaDAO dao = new RevistaDAO();
    private LibroDAO libroDAO = new LibroDAO();
    private DvdDAO dvdDAO = new DvdDAO();
    private CdAudioDAO cdAudioDAO = new CdAudioDAO();

    private ArrayList<Libro> librosActuales = new ArrayList<>();
    private ArrayList<Revista> revistasActuales = new ArrayList<>();
    private ArrayList<Dvd> dvdsActuales = new ArrayList<>();
    private ArrayList<CdAudio> cdsActuales = new ArrayList<>();

    private String codigoActual = "";

    public _panelGestion() {
        // Panel principal con BorderLayout
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);

        // --- Panel superior: formulario simple ---
        JPanel formulario = new JPanel(new GridLayout(2, 2, 10, 10));
        formulario.setBackground(COLOR_FONDO);
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTipoMaterial = new JLabel("Seleccione un Tipo de Material:");
        lblTipoMaterial.setForeground(COLOR_TEXTO);
        cboxTipoMaterial = new JComboBox<>(new String[]{"Revista", "Libro", "CD-Audio", "DVD"});
        cboxTipoMaterial.setBackground(Color.WHITE);
        cboxTipoMaterial.setForeground(Color.BLACK);

        JLabel lblCodigoTitulo = new JLabel("Código de material seleccionado:");
        lblCodigoTitulo.setForeground(COLOR_TEXTO);
        lblCodigo = new JLabel("");
        lblCodigo.setForeground(COLOR_BOTON_AZUL);

        formulario.add(lblTipoMaterial);
        formulario.add(cboxTipoMaterial);
        formulario.add(lblCodigoTitulo);
        formulario.add(lblCodigo);

        add(formulario, BorderLayout.NORTH);

        // --- Panel central: tabla simple ---
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Título", "Editorial", "Unidades", "Periodicidad", "Fecha"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setSelectionBackground(COLOR_BOTON_AZUL);
        tabla.setSelectionForeground(Color.WHITE);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // --- Panel inferior: botones simples ---
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setBackground(COLOR_BOTON_AZUL);
        btnModificar.setForeground(COLOR_TEXTO);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(COLOR_BOTON_ROJO);
        btnEliminar.setForeground(COLOR_TEXTO);
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(COLOR_TEXTO);
        btnSalir.setForeground(COLOR_FONDO);

        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);
        panelBotones.add(btnModificar);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Eventos ---

        // Cambio en tipo de material para listar
        cboxTipoMaterial.addActionListener(e -> {
            String tipo = (String) cboxTipoMaterial.getSelectedItem();
            if (tipo == null) return;
            modeloTabla.setRowCount(0);
            switch (tipo) {
                case "Revista":
                    listarRevistas();
                    break;
                case "Libro":
                    listarLibros();
                    break;
                case "DVD":
                    listarDvds();
                    break;
                case "CD-Audio":
                    listarCDAudios();
                    break;
            }
        });

        // Selección en tabla para actualizar código
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                String tipo = (String) cboxTipoMaterial.getSelectedItem();
                if (tipo == null) return;
                switch (tipo) {
                    case "Revista":
                        Revista r = revistasActuales.get(fila);
                        actualizarCodigo(r.getCodigo());
                        break;
                    case "Libro":
                        Libro l = librosActuales.get(fila);
                        actualizarCodigo(l.getCodigo());
                        break;
                    case "DVD":
                        Dvd d = dvdsActuales.get(fila);
                        actualizarCodigo(d.getCodigo());
                        break;
                    case "CD-Audio":
                        CdAudio cd = cdsActuales.get(fila);
                        actualizarCodigo(cd.getCodigo());
                        break;
                }
            }
        });

        // Botón eliminar con acción básica, similar para modificar y salir
        btnEliminar.addActionListener(e -> {
            if (codigoActual.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona un material en la tabla para eliminar.");
                return;
            }
            String tipo = (String) cboxTipoMaterial.getSelectedItem();
            if (tipo == null) return;
            try {
                switch (tipo) {
                    case "Revista":
                        dao.eliminarRevista(codigoActual);
                        JOptionPane.showMessageDialog(this, "Revista eliminada correctamente.");
                        limpiarCampos();
                        listarRevistas();
                        break;
                    case "Libro":
                        libroDAO.eliminarLibro(codigoActual);
                        JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
                        limpiarCampos();
                        listarLibros();
                        break;
                    case "DVD":
                        dvdDAO.eliminarDvd(codigoActual);
                        JOptionPane.showMessageDialog(this, "DVD eliminado correctamente.");
                        limpiarCampos();
                        listarDvds();
                        break;
                    case "CD-Audio":
                        cdAudioDAO.eliminarCd(codigoActual);
                        JOptionPane.showMessageDialog(this, "CD Audio eliminado correctamente.");
                        limpiarCampos();
                        listarCDAudios();
                        break;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
            }
        });

        btnSalir.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana != null) ventana.dispose();
        });

        btnModificar.addActionListener(e -> {
            if (tabla.isEditing()) tabla.getCellEditor().stopCellEditing();
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un material primero.");
                return;
            }
            String tipo = (String) cboxTipoMaterial.getSelectedItem();
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (tipo == null || ventana == null) return;
            switch (tipo) {
                case "Revista":
                    new _DialogRevista(ventana, revistasActuales.get(fila));
                    break;
                case "Libro":
                    new _DialogLibro(ventana, librosActuales.get(fila));
                    break;
                case "DVD":
                    new _DialogDvd(ventana, dvdsActuales.get(fila));
                    break;
                case "CD-Audio":
                    new _DialogCdAudio(ventana, cdsActuales.get(fila));
                    break;
            }
        });

        // Carga inicial
        cboxTipoMaterial.setSelectedIndex(0);
    }

    private void actualizarCodigo(String codigo) {
        codigoActual = codigo;
        lblCodigo.setText(codigo);
    }

    // Métodos listar sin cambios, sólo limpiados para claridad

    private void listarRevistas() {
        try {
            modeloTabla.setRowCount(0);
            modeloTabla.setColumnIdentifiers(new String[]{
                    "Código", "Título", "Editorial", "Unidades", "Periodicidad", "Fecha"
            });
            revistasActuales = dao.listarRevistas();
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
            librosActuales = libroDAO.listarLibros();
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
            dvdsActuales = dvdDAO.listarDvds();
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
            cdsActuales = cdAudioDAO.listarCds();
            for (CdAudio cd : cdsActuales) {
                modeloTabla.addRow(new Object[]{
                        cd.getCodigo(), cd.getTitulo(), cd.getArtista(),
                        cd.getUnidadesDisponibles(), cd.getGenero(),
                        cd.getDuracion(), cd.getNumeroCanciones()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar CD-Audio: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        lblCodigo.setText("");
    }
}
