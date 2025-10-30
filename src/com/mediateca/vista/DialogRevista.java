package com.mediateca.vista;

import com.mediateca.modelo.Revista;

import javax.swing.*;
import java.awt.*;

public class DialogRevista extends JDialog {
    public DialogRevista(Window parent, Revista revista) {
        super(parent, "Sistema de Mediateca - com.mediateca.modelo.Revista", ModalityType.APPLICATION_MODAL);
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new panelRevista(revista);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Para modo crear (sin datos)
    public DialogRevista(Window parent) {
        this(parent, null);
    }
}
