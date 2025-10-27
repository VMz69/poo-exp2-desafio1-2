import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // SwingUtilities.invokeLater(() -> new VentanaPrincipal());
            // para forzar el tema por defecto de swing e ignorar los estilos de macOS
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new _MainFrame());
    }
}

//Validar con los compañeros para que es VentanaPrincipal()
