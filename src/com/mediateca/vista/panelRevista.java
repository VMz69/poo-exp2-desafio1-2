package com.mediateca.vista;

import com.mediateca.dao.RevistaDAO;
import com.mediateca.modelo.Revista;
import com.mediateca.util.CodigoGenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class panelRevista extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtEditorial, txtUnidades, txtPeriodicidad, txtFecha;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RevistaDAO dao = new RevistaDAO();
    private String codigoActual = "";

    private Revista revista; // 🔹 atributo para edición

    public panelRevista(Revista revista) {
        this.revista = revista; // 🔹 guardamos la revista si viene para edición

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

        // Si recibimos una revista, cargamos sus datos
        if (this.revista != null) {
            cargarRevistaEnCampos();
        }
    }

    private JPanel crearPanelFormulario() {
        JPanel formulario = new JPanel(new GridLayout(6, 2, 15, 15));
        formulario.setBackground(new Color(250, 250, 250));
        formulario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 220), 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        Font fontLabel = new Font("Arial", Font.BOLD, 14);

        lblCodigo = new JLabel("REVxxxxx", JLabel.CENTER);
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 16));
        lblCodigo.setForeground(new Color(30, 100, 200));
        lblCodigo.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 220), 1));
        lblCodigo.setOpaque(true);
        lblCodigo.setBackground(new Color(245, 245, 255));

        formulario.add(new JLabel("Código generado:"));
        formulario.add(lblCodigo);

        txtTitulo = crearTextFieldEstilizado();
        formulario.add(new JLabel("Título:"));
        formulario.add(txtTitulo);

        txtEditorial = crearTextFieldEstilizado();
        formulario.add(new JLabel("Editorial:"));
        formulario.add(txtEditorial);

        txtUnidades = crearTextFieldEstilizado();
        formulario.add(new JLabel("Unidades disponibles:"));
        formulario.add(txtUnidades);

        txtPeriodicidad = crearTextFieldEstilizado();
        formulario.add(new JLabel("Periodicidad:"));
        formulario.add(txtPeriodicidad);

        txtFecha = crearTextFieldEstilizado();
        formulario.add(new JLabel("Fecha publicación (YYYY-MM-DD):"));
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

        JButton btnAgregar = crearBotonEstilizado("Guardar", new Color(80, 150, 80));
        btnAgregar.addActionListener(e -> guardarRevista());

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
            public void mouseEntered(java.awt.event.MouseEvent evt) { boton.setBackground(colorBase.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { boton.setBackground(colorBase); }
            public void mousePressed(java.awt.event.MouseEvent evt) { boton.setBackground(colorBase.darker()); }
        });

        return boton;
    }

    private void guardarRevista() {
        try {
            if (txtTitulo.getText().trim().isEmpty() ||
                    txtEditorial.getText().trim().isEmpty() ||
                    txtUnidades.getText().trim().isEmpty() ||
                    txtPeriodicidad.getText().trim().isEmpty() ||
                    txtFecha.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, complete todos los campos.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (revista != null) { // edición
                Revista editada = new Revista(
                        revista.getCodigo(), // mantiene el mismo código
                        txtTitulo.getText(),
                        txtEditorial.getText(),
                        Integer.parseInt(txtUnidades.getText()),
                        txtPeriodicidad.getText(),
                        LocalDate.parse(txtFecha.getText())
                );
                dao.actualizarRevista(editada);
                JOptionPane.showMessageDialog(this, "✓ Revista actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Cerrar la ventana padre (JDialog o JFrame)
                java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }
            } else { // creación
                codigoActual = CodigoGenerator.generarCodigo("revista");
                lblCodigo.setText(codigoActual);

                Revista nueva = new Revista(
                        codigoActual,
                        txtTitulo.getText(),
                        txtEditorial.getText(),
                        Integer.parseInt(txtUnidades.getText()),
                        txtPeriodicidad.getText(),
                        LocalDate.parse(txtFecha.getText())
                );
                dao.insertarRevista(nueva);
                JOptionPane.showMessageDialog(this, "✓ Revista guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Cerrar la ventana padre (JDialog o JFrame)
                java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }
            }

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Las unidades deben ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarRevistaEnCampos() {
        lblCodigo.setText(revista.getCodigo());
        txtTitulo.setText(revista.getTitulo());
        txtEditorial.setText(revista.getEditorial());
        txtUnidades.setText(String.valueOf(revista.getUnidadesDisponibles()));
        txtPeriodicidad.setText(revista.getPeriodicidad());
        txtFecha.setText(revista.getFechaPublicacion().toString());
    }

    private void limpiarCampos() {
        lblCodigo.setText("REVxxxxx");
        codigoActual = "";
        txtTitulo.setText("");
        txtEditorial.setText("");
        txtUnidades.setText("");
        txtPeriodicidad.setText("");
        txtFecha.setText("");
        revista = null;
        txtTitulo.requestFocus();
    }
}