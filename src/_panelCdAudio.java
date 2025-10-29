import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class _panelCdAudio extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtArtista, txtDuracion, txtUnidades, txtGenero, txtCanciones;
    private String codigoActual = "";

    private CdAudio cdAudio;           // para edición
    private CdAudioDAO cdAudioDAO = new CdAudioDAO();

    public _panelCdAudio(CdAudio cdAudio) {
        this.cdAudio = cdAudio;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel tituloPrincipal = new JLabel("Gestión de CD-Audio", JLabel.CENTER);
        tituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        tituloPrincipal.setForeground(new Color(70, 70, 120));
        tituloPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(tituloPrincipal, BorderLayout.NORTH);

        // Formulario
        JPanel formulario = crearPanelFormulario();
        add(formulario, BorderLayout.CENTER);

        // Botones
        JPanel botones = crearPanelBotones();
        add(botones, BorderLayout.SOUTH);

        // Si es edición, carga datos
        if (this.cdAudio != null) {
            cargarCdAudioEnCampos();
        }
    }

    private JPanel crearPanelFormulario() {
        JPanel formulario = new JPanel(new GridLayout(7, 2, 15, 15));
        formulario.setBackground(new Color(250, 250, 250));
        formulario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 220), 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        lblCodigo = new JLabel("CDAxxxxx", JLabel.CENTER);
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 16));
        lblCodigo.setForeground(new Color(30, 100, 200));
        lblCodigo.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 220), 1));
        lblCodigo.setOpaque(true);
        lblCodigo.setBackground(new Color(245, 245, 255));

        formulario.add(new JLabel("Código generado:"));
        formulario.add(lblCodigo);

        txtTitulo = crearTextFieldEstilizado();
        formulario.add(new JLabel("Título:", JLabel.LEFT));
        formulario.add(txtTitulo);

        txtArtista = crearTextFieldEstilizado();
        formulario.add(new JLabel("Artista:", JLabel.LEFT));
        formulario.add(txtArtista);

        txtDuracion = crearTextFieldEstilizado();
        formulario.add(new JLabel("Duración (min):", JLabel.LEFT));
        formulario.add(txtDuracion);

        txtUnidades = crearTextFieldEstilizado();
        formulario.add(new JLabel("Unidades disponibles:", JLabel.LEFT));
        formulario.add(txtUnidades);

        txtGenero = crearTextFieldEstilizado();
        formulario.add(new JLabel("Género:", JLabel.LEFT));
        formulario.add(txtGenero);

        txtCanciones = crearTextFieldEstilizado();
        formulario.add(new JLabel("Nº de canciones:", JLabel.LEFT));
        formulario.add(txtCanciones);

        return formulario;
    }

    private JTextField crearTextFieldEstilizado() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 220), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(60, 60, 80));

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(100, 130, 255), 2),
                        BorderFactory.createEmptyBorder(7, 9, 7, 9)
                ));
                textField.setBackground(new Color(255, 255, 245));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(180, 180, 220), 1),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
                textField.setBackground(Color.WHITE);
            }
        });

        return textField;
    }

    private JPanel crearPanelBotones() {
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botones.setBackground(new Color(240, 240, 240));
        botones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton btnCancelar = crearBotonEstilizado("Cancelar", new Color(220, 100, 100));
        btnCancelar.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) ventanaPadre.dispose();
        });

        JButton btnGuardar = crearBotonEstilizado("Guardar", new Color(80, 150, 80));
        btnGuardar.addActionListener(e -> guardarCdAudio());

        botones.add(btnCancelar);
        botones.add(btnGuardar);

        return botones;
    }

    private JButton crearBotonEstilizado(String texto, Color colorBase) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setBackground(colorBase);
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(colorBase.darker(), 1),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { boton.setBackground(colorBase.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { boton.setBackground(colorBase); }
            public void mousePressed(java.awt.event.MouseEvent evt) { boton.setBackground(colorBase.darker()); }
        });

        return boton;
    }

    private void guardarCdAudio() {
        try {
            if (txtTitulo.getText().trim().isEmpty() ||
                    txtArtista.getText().trim().isEmpty() ||
                    txtDuracion.getText().trim().isEmpty() ||
                    txtUnidades.getText().trim().isEmpty() ||
                    txtGenero.getText().trim().isEmpty() ||
                    txtCanciones.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Por favor complete todos los campos.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            int unidades = Integer.parseInt(txtUnidades.getText().trim());
            int canciones = Integer.parseInt(txtCanciones.getText().trim());

            if (cdAudio != null) { // edición
                CdAudio editado = new CdAudio(
                        cdAudio.getCodigo(),
                        txtTitulo.getText(),
                        duracion,
                        unidades,
                        txtGenero.getText(),
                        txtArtista.getText(),
                        canciones
                );
                cdAudioDAO.actualizarCd(editado);
                JOptionPane.showMessageDialog(this, "✓ CD-Audio actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Cerrar la ventana padre (JDialog o JFrame)
                java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }

            } else { // nuevo
                codigoActual = CodigoGenerator.generarCodigo("cd");
                lblCodigo.setText(codigoActual);

                CdAudio nuevo = new CdAudio(
                        codigoActual,
                        txtTitulo.getText(),
                        duracion,
                        unidades,
                        txtGenero.getText(),
                        txtArtista.getText(),
                        canciones
                );
                cdAudioDAO.insertarCd(nuevo);
                JOptionPane.showMessageDialog(this, "✓ CD-Audio guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Cerrar la ventana padre (JDialog o JFrame)
                java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }
            }

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duración, unidades y nº de canciones deben ser números válidos.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCdAudioEnCampos() {
        lblCodigo.setText(cdAudio.getCodigo());
        txtTitulo.setText(cdAudio.getTitulo());
        txtArtista.setText(cdAudio.getArtista());
        txtDuracion.setText(String.valueOf(cdAudio.getDuracion()));
        txtUnidades.setText(String.valueOf(cdAudio.getUnidadesDisponibles()));
        txtGenero.setText(cdAudio.getGenero());
        txtCanciones.setText(String.valueOf(cdAudio.getNumeroCanciones()));
    }

    private void limpiarCampos() {
        lblCodigo.setText("CDAxxxxx");
        codigoActual = "";
        txtTitulo.setText("");
        txtArtista.setText("");
        txtDuracion.setText("");
        txtUnidades.setText("");
        txtGenero.setText("");
        txtCanciones.setText("");
        cdAudio = null;
        txtTitulo.requestFocus();
    }
}