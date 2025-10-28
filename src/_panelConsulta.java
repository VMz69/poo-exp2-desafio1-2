import javax.swing.*;
import java.awt.*;

public class _panelConsulta extends JPanel {
    public _panelConsulta() {
        setLayout(new BorderLayout());

        JPanel panelNorth = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("CONSULTAR Y LISTAR MATERIALES DE MEDIATECA", JLabel.CENTER);
        panelNorth.add(lblTitulo, BorderLayout.NORTH);
        add(panelNorth, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Libros", new _tabLibro());
        //tabs.addTab("Revistas", new PanelRevista());
        //tabs.addTab("CDs de Audio", new PanelCdAudio());
        //tabs.addTab("DVDs", new PanelDvd());
        //tabs.addTab("Materiales disponibles", new PanelMaterialDisponible());

        add(tabs, BorderLayout.CENTER);

    }
}
