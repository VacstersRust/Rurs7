package block;

import tools.DirectoryTree;
import window.SettingsWindow;

import javax.swing.*;

public class SettingsBlock extends JPanel {
    private DirectoryTree directoryTree;

    public SettingsBlock(DirectoryTree directoryTree) {
        this.directoryTree = directoryTree;


        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JButton fileButton = new JButton("File");
        JButton settingsButton = new JButton("Settings");

        add(Box.createHorizontalStrut(5)); // Padding
        add(fileButton);
        add(Box.createHorizontalStrut(5)); // Padding
        add(settingsButton);

        settingsButton.addActionListener(e -> {
            SettingsWindow settingsWindow = new SettingsWindow();
            settingsWindow.setVisible(true);
        });

        fileButton.addActionListener(e -> {
            String selectedPath = tools.OpenDirectory.main();
            directoryTree.createDirectoryTreeFromPath(selectedPath);
        });
    }
}