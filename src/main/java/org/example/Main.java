package org.example;

import org.example.block.BaseBlock;
import org.example.block.DrawBlock;

import javax.swing.*;
import java.awt.*;

import static org.example.GlobalConstants.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Graph Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        BaseBlock baseBlock = new BaseBlock(BACKGROUND_COLOR);
        DrawBlock drawBlock = new DrawBlock();
/*        drawBlock.setBackground(Color.WHITE);
        frame.add(drawBlock, BorderLayout.CENTER);*/
        baseBlock.add(drawBlock);
        baseBlock.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
        frame.add(baseBlock);

        frame.setVisible(true);
    }
}
   /* static class BaseBlock extends JPanel {
        public BaseBlock(Color Graph_color) {
            setLayout(new BorderLayout());
            setBackground(Graph_color);

            DrawBlock drawBlock = new DrawBlock();
            drawBlock.setBackground(Color.WHITE);
            add(drawBlock, BorderLayout.CENTER);

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

    static class DrawBlock extends JPanel {
        private ArrayList<Integer> xValues;
        private ArrayList<Integer> yValues;
        private double xMin = -10.0;
        private double xMax = 10.0;
        private double yMin = -10.0;
        private double yMax = 10.0;

        private boolean isSelecting = false;
        private int startX, endX;
        private int startY, endY;

        public DrawBlock() {
            setBackground(Color.WHITE);
            xValues = new ArrayList<>();
            yValues = new ArrayList<>();
            for (int i = -100; i <= 100; i++) {
                xValues.add(i);
                yValues.add(i * i);
            }

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        startX = e.getX();
                        startY = e.getY();
                        isSelecting = true;
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        endX = e.getX();
                        endY = e.getY();
                        isSelecting = false;

                        double scaleX = (xMax - xMin) / getWidth();
                        double scaleY = (yMax - yMin) / getHeight();

                        xMin += scaleX * Math.min(startX, endX);
                        xMax -= scaleX * (getWidth() - Math.max(startX, endX));
                        yMin += scaleY * (getHeight() - Math.max(startY, endY));
                        yMax -= scaleY * Math.min(startY, endY);
                        paintComponent(getGraphics());
                    }
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int width = getWidth();
            int height = getHeight();

            g2.setColor(Color.BLACK);
            g2.drawLine(50, height - 50, width - 50, height - 50);
            g2.drawLine(50, height - 50, 50, 50);

            g2.drawString("x", width - 20, height - 40);
            g2.drawString("y", 20, 20);

            int xScale = (width + 100) / (xValues.size() - 1);
            int yScale = (height + 100) / (yValues.size() - 1);

            for (int i = 0; i < xValues.size(); i += 20) {
                int x = 50 + i * xScale;
                g2.drawLine(x, height - 50, x, 50);
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(xValues.get(i)), x - LABEL_PADDING, height - 30);
            }

            for (int i = 0; i < yValues.size(); i += 20) {
                int y = height - 50 - i * yScale;
                g2.drawLine(50, y, width - 50, y);
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(yValues.get(i)), 25, y + LABEL_PADDING);
            }
            // Сетка
            g2.setColor(FUNCTION_COLOR);
            g2.setStroke(new BasicStroke(FUNCTION_THICKNESS));
            for (int i = 0; i < xValues.size() - 1; i++) {
                int x1 = 50 + i * xScale;
                int y1 = height - 50 - yValues.get(i) * yScale / 100;
                int x2 = 50 + (i + 1) * xScale;
                int y2 = height - 50 - yValues.get(i + 1) * yScale / 100;
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

    static class SettingsBlock extends JPanel {
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

    static class SettingsWindow extends JFrame {
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

    static class ToolKit extends JPanel {
        public ToolKit(DrawBlock drawBlock) {
            setLayout(new FlowLayout());
            setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
            setBackground(Color.DARK_GRAY);
        }
    }
}*/
