package window;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JFrame {
    public SettingsWindow() {
        setTitle("Settings Window");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel settingsPanel = new JPanel(new GridLayout(2, 1));

        JButton helloButton = new JButton("Привет");
        JButton drawGraphButton = new JButton("Draw Graph");

        settingsPanel.add(helloButton);
        settingsPanel.add(drawGraphButton);

        add(settingsPanel);
    }
}
