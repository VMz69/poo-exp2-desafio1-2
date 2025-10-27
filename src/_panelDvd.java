import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class _panelDvd extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtDuracion, txtUnidades, txtGenero, txtDirector;
    private DvdDAO dao = new DvdDAO();
    private String codigoActual = "";

    public _panelDvd() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tituloPrincipal = new JLabel("Gestión de DVDs", JLabel.CENTER);
        tituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        tituloPrincipal.setForeground(new Color(70, 70, 120));
        tituloPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(tituloPrincipal, BorderLayout.NORTH);

        JPanel formulario = crearPanelFormulario();
        add(formulario, BorderLayout.CENTER);

        JPanel botones = crearPanelBotones();
        add(botones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {
        JPanel formulario = new JPanel(new GridLayout(6, 2, 15, 15));
        formulario.setBackground(new Color(250, 250, 250));
        formulario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 220), 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        Font fontLabel = new Font("Arial", Font.BOLD, 14);

        // Código generado
        lblCodigo = new JLabel("DVDxxxxx", JLabel.CENTER);
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 16));
        lblCodigo.setForeground(new Color(30, 100, 200));
        lblCodigo.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 220), 1));
        lblCodigo.setOpaque(true);
        lblCodigo.setBackground(new Color(245, 245, 255));

        JLabel lblCodigoTexto = new JLabel("Código generado:");
        lblCodigoTexto.setFont(fontLabel);
        lblCodigoTexto.setForeground(new Color(80, 80, 120));
        formulario.add(lblCodigoTexto);
        formulario.add(lblCodigo);

        // Título
        txtTitulo = crearTextFieldEstilizado();
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(fontLabel);
        lblTitulo.setForeground(new Color(80, 80, 120));
        formulario.add(lblTitulo);
        formulario.add(txtTitulo);

        // Duración
        txtDuracion = crearTextFieldEstilizado();
        JLabel lblDuracion = new JLabel("Duración (minutos):");
        lblDuracion.setFont(fontLabel);
        lblDuracion.setForeground(new Color(80, 80, 120));
        formulario.add(lblDuracion);
        formulario.add(txtDuracion);

        // Unidades disponibles
        txtUnidades = crearTextFieldEstilizado();
        JLabel lblUnidades = new JLabel("Unidades disponibles:");
        lblUnidades.setFont(fontLabel);
        lblUnidades.setForeground(new Color(80, 80, 120));
        formulario.add(lblUnidades);
        formulario.add(txtUnidades);

        // Género
        txtGenero = crearTextFieldEstilizado();
        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setFont(fontLabel);
        lblGenero.setForeground(new Color(80, 80, 120));
        formulario.add(lblGenero);
        formulario.add(txtGenero);

        // Director
        txtDirector = crearTextFieldEstilizado();
        JLabel lblDirector = new JLabel("Director:");
        lblDirector.setFont(fontLabel);
        lblDirector.setForeground(new Color(80, 80, 120));
        formulario.add(lblDirector);
        formulario.add(txtDirector);

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
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });

        JButton btnAgregar = crearBotonEstilizado("Agregar DVD", new Color(80, 150, 80));
        btnAgregar.addActionListener(e -> agregarDvd());

        botones.add(btnCancelar);
        botones.add(btnAgregar);

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase.darker());
            }
        });

        return boton;
    }

    private void agregarDvd() {
        try {
            if (txtTitulo.getText().trim().isEmpty() ||
                    txtDuracion.getText().trim().isEmpty() ||
                    txtUnidades.getText().trim().isEmpty() ||
                    txtGenero.getText().trim().isEmpty() ||
                    txtDirector.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Por favor, complete todos los campos.",
                        "Campos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            codigoActual = CodigoGenerator.generarCodigo("dvd");
            lblCodigo.setText(codigoActual);

            Dvd dvd = new Dvd(
                    codigoActual,
                    txtTitulo.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    Integer.parseInt(txtUnidades.getText()),
                    txtGenero.getText(),
                    txtDirector.getText()
            );
            dao.insertarDvd(dvd);

            JOptionPane.showMessageDialog(this,
                    "✓ DVD guardado exitosamente en la base de datos\nCódigo: " + codigoActual,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: La duración y las unidades deben ser números válidos.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage()
            );
        }
    }

    private void limpiarCampos() {
        lblCodigo.setText("DVDxxxxx");
        lblCodigo.setForeground(new Color(30, 100, 200));
        codigoActual = "";
        txtTitulo.setText("");
        txtDuracion.setText("");
        txtUnidades.setText("");
        txtGenero.setText("");
        txtDirector.setText("");
        txtTitulo.requestFocus();
    }
}
