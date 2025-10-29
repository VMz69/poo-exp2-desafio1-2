import javax.swing.*;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class Main {
    public static void main(String[] args) {

        // Si es Mac
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        try {
            FlatMacLightLaf.setup();
        } catch (Throwable t) {
            // Si no es Mac, usar el tema cross platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> new _MainFrame());
    }
}
