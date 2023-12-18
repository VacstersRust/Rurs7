package org.example.block;

import org.example.tools.ToolKit;

import javax.swing.*;
import java.awt.*;

import static org.example.GlobalConstants.*;

public class BaseBlock extends JPanel {
    public BaseBlock(Color Graph_color) {
        setLayout(new BorderLayout());
        setBackground(Graph_color);

        this.setBackground(Color.WHITE);

        SettingsBlock settingsBlock = new SettingsBlock();
        settingsBlock.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        settingsBlock.setBackground(FILE_COLOR);
        settingsBlock.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        add(settingsBlock, BorderLayout.NORTH);

        ToolKit toolKitBlock = new ToolKit(new DrawBlock());
        toolKitBlock.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        toolKitBlock.setBackground(TOOLS_COLOR);
        add(toolKitBlock, BorderLayout.SOUTH);
    }
}
