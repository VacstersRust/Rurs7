package org.example;

import javax.swing.*;
import java.awt.*;

public class BaseBlock extends JPanel {
    public BaseBlock(Color Graph_color) {
        setLayout(new BorderLayout());
        setBackground(Graph_color);

        this.setBackground(Color.WHITE);
        add(this, BorderLayout.CENTER);

        SettingsBlock settingsBlock = new SettingsBlock();
        settingsBlock.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        settingsBlock.setBackground(File_color);
        settingsBlock.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        add(settingsBlock, BorderLayout.NORTH);

        ToolKit toolKitBlock = new ToolKit(drawBlock);
        toolKitBlock.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        toolKitBlock.setBackground(Tools_color);
        add(toolKitBlock, BorderLayout.SOUTH);
    }
}
