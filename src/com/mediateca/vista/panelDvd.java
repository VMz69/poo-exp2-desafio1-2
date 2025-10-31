package com.mediateca.vista;

import com.mediateca.dao.DvdDAO;
import com.mediateca.modelo.Dvd;
import com.mediateca.util.CodigoGenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class panelDvd extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtDirector, txtDuracion, txtUnidades, txtGenero;
    private String codigoActual = "";
    private JTable tabla;

    private DefaultTableModel modeloTabla;
    private Dvd dvd; // para edición
    private DvdDAO dvdDAO = new DvdDAO();

    public panelDvd(Dvd dvd) {
        this.dvd = dvd;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel tituloPrincipal = new JLabel("Gestión de DVDs", JLabel.CENTER);
        tituloPrincipal.setFont(new Font("Arial", Font.BOLD, 28));
        tituloPrincipal.setForeground(new Color(70, 70, 120));
        tituloPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(tituloPrincipal, BorderLayout.NORTH);

        // Formulario central
        JPanel formulario = crearPanelFormulario();
        add(formulario, BorderLayout.CENTER);

        // Panel de botones inferior
        JPanel botones = crearPanelBotones();
        add(botones, BorderLayout.SOUTH);

        // Si es edición, cargar datos
        if (this.dvd != null) {
            cargarDvdEnCampos();
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

        lblCodigo = new JLabel("DVDxxxxx", JLabel.CENTER);
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

        txtDirector = crearTextFieldEstilizado();
        formulario.add(new JLabel("Director:", JLabel.LEFT));
        formulario.add(txtDirector);

        txtDuracion = crearTextFieldEstilizado();
        formulario.add(new JLabel("Duración (min):", JLabel.LEFT));
        formulario.add(txtDuracion);

        txtUnidades = crearTextFieldEstilizado();
        formulario.add(new JLabel("Unidades disponibles:", JLabel.LEFT));
        formulario.add(txtUnidades);

        txtGenero = crearTextFieldEstilizado();
        formulario.add(new JLabel("Género:", JLabel.LEFT));
        formulario.add(txtGenero);

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

        JButton btnCancelar = new JButton("Salir");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(255, 107, 107)); // Rojo suave de la paleta
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15)); // Padding interno
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> {
            Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
            if (ventanaPadre != null) ventanaPadre.dispose();
        });

        // Efecto hover sencillo
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(230, 85, 85));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(255, 107, 107));
            }
        });

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBackground(new Color(80, 150, 80)); // Verde suave de la paleta
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(65, 130, 65));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(80, 150, 80));
            }
        });
        btnGuardar.addActionListener(e -> guardarDvd());

        botones.add(btnCancelar);
        botones.add(btnGuardar);

        return botones;
    }


    private void guardarDvd() {
        try {
            if (txtTitulo.getText().trim().isEmpty() ||
                    txtDirector.getText().trim().isEmpty() ||
                    txtDuracion.getText().trim().isEmpty() ||
                    txtUnidades.getText().trim().isEmpty() ||
                    txtGenero.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Por favor complete todos los campos.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            int unidades = Integer.parseInt(txtUnidades.getText().trim());

            if (dvd != null) { // modo edición
                Dvd editado = new Dvd(
                        dvd.getCodigo(),
                        txtTitulo.getText(),
                        duracion,
                        unidades,
                        txtGenero.getText(),
                        txtDirector.getText()
                );
                dvdDAO.actualizarDvd(editado);
                JOptionPane.showMessageDialog(this, "✓ DVD actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Cerrar la ventana padre (JDialog o JFrame)
                java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }

            } else { // nuevo registro
                codigoActual = CodigoGenerator.generarCodigo("dvd");
                lblCodigo.setText(codigoActual);

                Dvd nuevo = new Dvd(
                        codigoActual,
                        txtTitulo.getText(),
                        duracion,
                        unidades,
                        txtGenero.getText(),
                        txtDirector.getText()
                );
                dvdDAO.insertarDvd(nuevo);
                JOptionPane.showMessageDialog(this, "✓ DVD guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Cerrar la ventana padre (JDialog o JFrame)
                java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }
            }

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duración y unidades deben ser números válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDvdEnCampos() {
        lblCodigo.setText(dvd.getCodigo());
        txtTitulo.setText(dvd.getTitulo());
        txtDirector.setText(dvd.getDirector());
        txtDuracion.setText(String.valueOf(dvd.getDuracion()));
        txtUnidades.setText(String.valueOf(dvd.getUnidadesDisponibles()));
        txtGenero.setText(dvd.getGenero());
    }

    private void limpiarCampos() {
        lblCodigo.setText("DVDxxxxx");
        codigoActual = "";
        txtTitulo.setText("");
        txtDirector.setText("");
        txtDuracion.setText("");
        txtUnidades.setText("");
        txtGenero.setText("");
        dvd = null;
        txtTitulo.requestFocus();
    }
}