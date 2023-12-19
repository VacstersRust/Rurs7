package org.example.tools;

import org.example.block.DrawBlock;

import javax.swing.*;
import java.awt.*;

import static org.example.GlobalConstants.*;

public class ToolBar extends JPanel {
    public ToolBar(DrawBlock drawBlock) {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        setBackground(Color.DARK_GRAY);
    }
}
