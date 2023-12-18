package org.example;

import javax.swing.*;

public class SettingsBlock extends JPanel {
    public SettingsBlock() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JButton fileButton = new JButton("File");
        JButton settingsButton = new JButton("Settings");

        add(Box.createHorizontalStrut(padding));
        add(fileButton);
        add(Box.createHorizontalStrut(padding));
        add(settingsButton);

        settingsButton.addActionListener(e -> {
            SettingsWindow settingsWindow = new SettingsWindow();
            settingsWindow.setVisible(true);
        });
    }
}
