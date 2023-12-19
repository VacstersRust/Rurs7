package org.example.block;

import org.example.tools.ToolBar;
import javax.swing.*;
import java.awt.*;

import static org.example.GlobalConstants.*;


public class BaseBlock extends JPanel {
    public BaseBlock() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(WIN_PADDING, WIN_PADDING, WIN_PADDING, WIN_PADDING));
        setBackground(BACKGROUND_COLOR);

        // Окно графика:
        DrawBlock drawBlock = new DrawBlock();
        add(drawBlock);

        // Панель управления:
        SettingsBlock settingsBlock = new SettingsBlock();
        settingsBlock.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        settingsBlock.setBackground(FILE_COLOR);
        add(settingsBlock, BorderLayout.NORTH);

        // Панель инструментов:
        ToolBar ToolBarBlock = new ToolBar(new DrawBlock());
        ToolBarBlock.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        ToolBarBlock.setBackground(TOOLS_COLOR);
        add(ToolBarBlock, BorderLayout.SOUTH);
    }
}
