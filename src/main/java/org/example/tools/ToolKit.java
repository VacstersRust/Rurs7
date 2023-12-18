package org.example.tools;

import org.example.block.DrawBlock;

import javax.swing.*;
import java.awt.*;

import static org.example.GlobalConstants.*;

public class ToolKit extends JPanel {
    public ToolKit(DrawBlock drawBlock) {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        setBackground(Color.DARK_GRAY);
    }
}
