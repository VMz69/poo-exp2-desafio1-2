package com.mediateca.vista;

import com.mediateca.modelo.CdAudio;

import javax.swing.*;
import java.awt.*;

public class DialogCdAudio extends JDialog {
    public DialogCdAudio(Window parent, CdAudio cdAudio) {
        super(parent, "Sistema de Mediateca - com.mediateca.modelo.Libro", ModalityType.APPLICATION_MODAL);
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new panelCdAudio(cdAudio); // con su respectivo panel
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Modo crear (sin datos)
    public DialogCdAudio(Window parent) {
        this(parent, null);
    }
}