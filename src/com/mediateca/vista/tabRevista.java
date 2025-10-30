package com.mediateca.vista;

import com.mediateca.dao.RevistaDAO;
import com.mediateca.modelo.Revista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class tabRevista extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RevistaDAO dao = new RevistaDAO();

    public tabRevista() {
        // --- Configuración general del panel ---
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Fondo claro general

        // ============================================================
        // PANEL SUPERIOR CON BOTONES
        // ============================================================
        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.setBackground(new Color(240, 240, 240));
        panelNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel para los botones centrados
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(new Color(240, 240, 240));

        // --- Botón "Listar todas las Revistas" ---
        JButton btnListarTodos = new JButton("Ver / Listar todas las Revistas");
        btnListarTodos.setFont(new Font("Arial", Font.BOLD, 13));
        btnListarTodos.setForeground(Color.WHITE);
        btnListarTodos.setBackground(new Color(45, 48, 80)); // Azul sobrio
        btnListarTodos.setFocusPainted(false);
        btnListarTodos.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnListarTodos.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btnListarTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnListarTodos.setBackground(new Color(60, 83, 166));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnListarTodos.setBackground(new Color(45, 48, 80));
            }
        });

        // --- Botón "Listar solo disponibles" ---
        JButton btnListarDisponibles = new JButton("Listar solo Revistas disponibles");
        btnListarDisponibles.setFont(new Font("Arial", Font.BOLD, 13));
        btnListarDisponibles.setForeground(Color.WHITE);
        btnListarDisponibles.setBackground(new Color(80, 150, 80)); // Verde de la paleta
        btnListarDisponibles.setFocusPainted(false);
        btnListarDisponibles.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        btnListarDisponibles.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover
        btnListarDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnListarDisponibles.setBackground(new Color(65, 130, 65));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnListarDisponibles.setBackground(new Color(80, 150, 80));
            }
        });

        // Agregar botones al panel
        panelBotones.add(btnListarTodos);
        panelBotones.add(btnListarDisponibles);

        // Añadir el panel de botones al norte
        panelNorth.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorth, BorderLayout.NORTH);

        // ============================================================
        // TABLA CENTRAL
        // ============================================================
        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Editorial", "Unidades", "Periodicidad", "Fecha"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace todas las celdas no editables
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(180, 180, 220));
        tabla.getTableHeader().setForeground(new Color(45, 48, 80));
        tabla.setRowHeight(22);
        tabla.setGridColor(new Color(200, 200, 200));
        tabla.setSelectionBackground(new Color(100, 130, 255));
        tabla.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // ============================================================
        // EVENTOS DE BOTONES
        // ============================================================
        btnListarTodos.addActionListener(e -> listarRevistas());
        btnListarDisponibles.addActionListener(e -> listarDisponibles());
    }

    // ------------------------------------------------------------
    // MÉTODOS DE LISTADO
    // ------------------------------------------------------------
    private void listarRevistas() {
        try {
            modeloTabla.setRowCount(0); // Limpia la tabla antes de listar
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

    private void listarDisponibles() {
        try {
            modeloTabla.setRowCount(0); // Limpia la tabla antes de listar
            ArrayList<Revista> revistas = dao.listarRevistas();
            for (Revista r : revistas) {
                if (r.getUnidadesDisponibles() > 0) {
                    modeloTabla.addRow(new Object[]{
                            r.getCodigo(), r.getTitulo(), r.getEditorial(),
                            r.getUnidadesDisponibles(), r.getPeriodicidad(),
                            r.getFechaPublicacion()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar revistas disponibles: " + ex.getMessage());
        }
    }
}
