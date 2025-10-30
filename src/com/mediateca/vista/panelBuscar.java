package com.mediateca.vista;

import com.mediateca.dao.MaterialDAO;
import com.mediateca.modelo.Material;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class panelBuscar extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtBusqueda;
    private MaterialDAO dao = new MaterialDAO();

    public panelBuscar() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Fondo general claro

        // ===============================
        // PANEL SUPERIOR - BÚSQUEDA
        // ===============================
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelSuperior.setBackground(new Color(240, 240, 240));

        JLabel lblTitulo = new JLabel("Busqueda de Materiales por Código y por Título:");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTitulo.setForeground(new Color(45, 48, 80));

        txtBusqueda = new JTextField(25);
        txtBusqueda.setFont(new Font("Arial", Font.PLAIN, 13));
        txtBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JButton btnBuscar = new JButton("Buscar");
        configurarBoton(btnBuscar, new Color(45, 48, 80), Color.WHITE);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(txtBusqueda);
        panelSuperior.add(btnBuscar);
        add(panelSuperior, BorderLayout.NORTH);

        // ===============================
        // TABLA CENTRAL
        // ===============================
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Título"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(5);   // Código más angosto
        tabla.getColumnModel().getColumn(1).setPreferredWidth(800);  // Título más ancho
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(180, 180, 220));
        tabla.getTableHeader().setForeground(new Color(45, 48, 80));
        tabla.setRowHeight(22);
        tabla.setSelectionBackground(new Color(100, 130, 255));
        tabla.setGridColor(new Color(200, 200, 200));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(240, 240, 240));
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scroll, BorderLayout.CENTER);

        // ===============================
        // PANEL INFERIOR - BOTÓN SALIR
        // ===============================
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelInferior.setBackground(new Color(240, 240, 240));

        JButton btnSalir = new JButton("Salir");
        configurarBoton(btnSalir, new Color(180, 80, 80), Color.WHITE);

        btnSalir.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            if (ventana != null) ventana.dispose();
        });

        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);

        // ===============================
        // EVENTOS
        // ===============================
        btnBuscar.addActionListener(e -> buscarYActualizarTabla());
        listarMateriales(); // al iniciar
    }

    // ---------------------- ESTILO ----------------------
    private void configurarBoton(JButton boton, Color fondo, Color texto) {
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.PLAIN, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // ---------------------- FUNCIONALIDAD ----------------------
    private void listarMateriales() {
        try {
            ArrayList<Material> materiales = dao.listarMateriales();
            actualizarTabla(materiales);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar materiales: " + ex.getMessage());
        }
    }

    private void buscarYActualizarTabla() {
        try {
            String texto = txtBusqueda.getText().trim();
            if (texto.isEmpty()) listarMateriales();
            else actualizarTabla(dao.buscarMateriales(texto));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en búsqueda: " + ex.getMessage());
        }
    }

    private void actualizarTabla(ArrayList<Material> materiales) {
        modeloTabla.setRowCount(0);
        for (Material m : materiales) {
            modeloTabla.addRow(new Object[]{m.getCodigo(), m.getTitulo()});
        }
    }
}
