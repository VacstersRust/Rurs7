package org.example.block;

import org.example.window.SettingsWindow;

import javax.swing.*;

import static org.example.GlobalConstants.PADDING;

public class SettingsBlock extends JPanel {
    public SettingsBlock() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JButton fileButton = new JButton("File");
        JButton settingsButton = new JButton("Settings");

        add(Box.createHorizontalStrut(PADDING));
        add(fileButton);
        add(Box.createHorizontalStrut(PADDING));
        add(settingsButton);

        settingsButton.addActionListener(e -> {
            SettingsWindow settingsWindow = new SettingsWindow();
            settingsWindow.setVisible(true);
        });
    }
}
