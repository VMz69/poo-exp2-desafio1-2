package com.mediateca;
import com.formdev.flatlaf.FlatLightLaf;
import com.mediateca.vista.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean usingFlatLaf = false;

            try {
                // Try FlatLaf first (better cross-platform UI)
                FlatLightLaf.setup();
                System.out.println("[LAF] FlatLaf detected ✓ Using FlatLightLaf");
                usingFlatLaf = true;
            } catch (Throwable e) {
                System.out.println("[LAF] FlatLaf not available — falling back…");
            }

            if (!usingFlatLaf) {
                try {
                    String sysLaf = UIManager.getSystemLookAndFeelClassName();
                    UIManager.setLookAndFeel(sysLaf);
                    System.out.println("[LAF] Using system Look & Feel: " + sysLaf);
                } catch (Exception ex) {
                    System.err.println("[LAF] ERROR — No compatible Look & Feel found. Using default.");
                }
            }

            new MainFrame();
        });
    }
}