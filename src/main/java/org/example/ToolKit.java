package org.example;

import javax.swing.*;

public class ToolKit extends JPanel {
    public ToolKit(DrawBlock drawBlock) {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        setBackground(Color.DARK_GRAY);
    }
}
}
