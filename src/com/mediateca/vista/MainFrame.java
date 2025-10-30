package com.mediateca.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Mediateca");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Cargar y asignar ícono ---
        ImageIcon icono = new ImageIcon(getClass().getResource("../resources/logoApp.png"));
        setIconImage(icono.getImage());


        // Crear panel principal con diseño centrado
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Panel para el título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 48, 80));
        JLabel titleLabel = new JLabel("Bienvenido al sistema de Mediateca");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Panel central para los botones
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear botones
        JButton btnAgregar = new JButton("Agregar Material Nuevo");
        JButton btnAdministrar = new JButton("Gestión de Materiales");
        JButton btnConsulta = new JButton("Consultar Materiales");
        JButton btnBuscar = new JButton("Busqueda de Materiales");
        JButton btnSalir = new JButton("Salir");

        // Estilizar botones
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        btnAgregar.setFont(buttonFont);
        btnAdministrar.setFont(buttonFont);
        btnConsulta.setFont(buttonFont);
        btnBuscar.setFont(buttonFont);
        btnSalir.setFont(buttonFont);

        btnAgregar.setBackground(new Color(45, 48, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAdministrar.setBackground(new Color(45, 48, 80));
        btnAdministrar.setForeground(Color.WHITE);
        btnConsulta.setBackground(new Color(45, 48, 80));
        btnConsulta.setForeground(Color.WHITE);
        btnBuscar.setBackground(new Color(45, 48, 80));
        btnBuscar.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(255, 107, 107));
        btnSalir.setForeground(Color.WHITE);

        btnAgregar.setPreferredSize(new Dimension(250, 50));
        btnAdministrar.setPreferredSize(new Dimension(250, 50));
        btnConsulta.setPreferredSize(new Dimension(250, 50));
        btnBuscar.setPreferredSize(new Dimension(250, 50));
        btnSalir.setPreferredSize(new Dimension(250, 50));

        // Agregar botones al panel central
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(btnAgregar, gbc);

        gbc.gridy = 1;
        centerPanel.add(btnAdministrar, gbc);

        gbc.gridy = 2;
        centerPanel.add(btnConsulta, gbc);

        gbc.gridy = 3;
        centerPanel.add(btnBuscar, gbc);

        gbc.gridy = 4;
        centerPanel.add(btnSalir, gbc);

        // Agregar action listeners
        btnAgregar.addActionListener(new ActionListener() {

            public Object[] opciones = {"Agregar Revista", "Agregar Libro", "Agregar CD", "Agregar DVD"}; //opciones para pintar dentro de botones de JOptionDialog
            public void actionPerformed(ActionEvent e) {
                //Componente JLabel para pintar dentro del optionDialog
                JLabel label = new JLabel("<html><br>Seleccionar el tipo de material que desea registrar:<br><hr><br></html>");
                label.setFont(new Font("Arial", Font.BOLD, 24));
                label.setForeground(Color.DARK_GRAY);

                //optionDialog
                int tipo = JOptionPane.showOptionDialog(
                        null,      // Componente padre
                        label,                   // Mensaje
                        "Seleccionar",          // Título
                        JOptionPane.DEFAULT_OPTION, // Tipo de opción (OK_CANCEL, YES_NO, etc.)
                        JOptionPane.QUESTION_MESSAGE, // Tipo de mensaje (ícono)
                        null,                   // Icono personalizado (null = por defecto)
                        opciones,               // Botones
                        "Desktop"               // Opción seleccionada por defecto
                );
                // Si el usuario cancela
                if (tipo == -1) {
                    return;
                }
                switch (tipo) {
                    case 0:
                        new DialogRevista(null);
                        break;
                    case 1:
                        new DialogLibro(null);
                        break;
                    case 2:
                        new DialogCdAudio(null);
                        break;
                    case 3:
                        new DialogDvd(null);
                        break;

                }

            }
        });

        btnAdministrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DialogGestion(null);
            }
        });

        btnConsulta.addActionListener(new ActionListener() { //Tercera opcion de menu principal (Consultar o listar materiales)
            @Override
            public void actionPerformed(ActionEvent e) {
                new DialogConsulta(null);
            }
        });

        btnBuscar.addActionListener(new ActionListener() { //Tercera opcion de menu principal (Consultar o listar materiales)
            @Override
            public void actionPerformed(ActionEvent e) {
                new DialogBuscar(null);
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MainFrame.this,
                        "¿Está seguro de que desea salir?", "Confirmar salida",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Agregar paneles al frame principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}