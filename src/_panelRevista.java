import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class _panelRevista extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtEditorial, txtUnidades, txtPeriodicidad, txtFecha;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RevistaDAO dao = new RevistaDAO();
    private String codigoActual = "";

    public _panelRevista() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título principal
        JLabel tituloPrincipal = new JLabel("Gestión de Revistas", JLabel.CENTER);
        tituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        tituloPrincipal.setForeground(new Color(70, 70, 120));
        tituloPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(tituloPrincipal, BorderLayout.NORTH);

        // Panel del formulario con estilo
        JPanel formulario = crearPanelFormulario();
        add(formulario, BorderLayout.CENTER);

        // Panel de botones con estilo
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

        // Configurar estilo de las etiquetas
        Font fontLabel = new Font("Arial", Font.BOLD, 14);
        Font fontField = new Font("Arial", Font.PLAIN, 14);

        // Código generado
        lblCodigo = new JLabel("REVxxxxx", JLabel.CENTER);
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

        // Editorial
        txtEditorial = crearTextFieldEstilizado();
        JLabel lblEditorial = new JLabel("Editorial:");
        lblEditorial.setFont(fontLabel);
        lblEditorial.setForeground(new Color(80, 80, 120));
        formulario.add(lblEditorial);
        formulario.add(txtEditorial);

        // Unidades disponibles
        txtUnidades = crearTextFieldEstilizado();
        JLabel lblUnidades = new JLabel("Unidades disponibles:");
        lblUnidades.setFont(fontLabel);
        lblUnidades.setForeground(new Color(80, 80, 120));
        formulario.add(lblUnidades);
        formulario.add(txtUnidades);

        // Periodicidad
        txtPeriodicidad = crearTextFieldEstilizado();
        JLabel lblPeriodicidad = new JLabel("Periodicidad:");
        lblPeriodicidad.setFont(fontLabel);
        lblPeriodicidad.setForeground(new Color(80, 80, 120));
        formulario.add(lblPeriodicidad);
        formulario.add(txtPeriodicidad);

        // Fecha publicación
        txtFecha = crearTextFieldEstilizado();
        JLabel lblFecha = new JLabel("Fecha publicación (YYYY-MM-DD):");
        lblFecha.setFont(fontLabel);
        lblFecha.setForeground(new Color(80, 80, 120));
        formulario.add(lblFecha);
        formulario.add(txtFecha);

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

        // Efecto focus/hover
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

        // Botón Cancelar
        JButton btnCancelar = crearBotonEstilizado("Cancelar", new Color(220, 100, 100));
        btnCancelar.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) {
                ventanaPadre.dispose();
            }
        });

        // Botón Agregar
        JButton btnAgregar = crearBotonEstilizado("Guardar", new Color(80, 150, 80));
        btnAgregar.addActionListener(e -> agregarRevista());

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

        // Efectos hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase.brighter());
                boton.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(colorBase.darker(), 1),
                        BorderFactory.createEmptyBorder(12, 25, 12, 25)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase);
                boton.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(colorBase.darker(), 1),
                        BorderFactory.createEmptyBorder(12, 25, 12, 25)
                ));
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase.darker());
            }
        });

        return boton;
    }

    private void agregarRevista() {
        try {
            // Validar campos vacíos
            if (txtTitulo.getText().trim().isEmpty() ||
                    txtEditorial.getText().trim().isEmpty() ||
                    txtUnidades.getText().trim().isEmpty() ||
                    txtPeriodicidad.getText().trim().isEmpty() ||
                    txtFecha.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Por favor, complete todos los campos.",
                        "Campos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            codigoActual = CodigoGenerator.generarCodigo("revista");
            lblCodigo.setText(codigoActual);

            Revista revista = new Revista(
                    codigoActual,
                    txtTitulo.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtUnidades.getText()),
                    txtPeriodicidad.getText(),
                    LocalDate.parse(txtFecha.getText())
            );
            dao.insertarRevista(revista);

            JOptionPane.showMessageDialog(this,
                    "✓ Revista guardada exitosamente en la base de datos\nCódigo: " + codigoActual,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Cerrar la ventana padre (JDialog o JFrame)
            java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
            if (parentWindow != null) {
                parentWindow.dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: Las unidades disponibles deben ser un número válido.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        lblCodigo.setText("REVxxxxx");
        lblCodigo.setForeground(new Color(30, 100, 200));
        codigoActual = "";
        txtTitulo.setText("");
        txtEditorial.setText("");
        txtUnidades.setText("");
        txtPeriodicidad.setText("");
        txtFecha.setText("");

        // Regresar el foco al primer campo
        txtTitulo.requestFocus();
    }
}