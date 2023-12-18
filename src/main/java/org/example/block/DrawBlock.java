package org.example.block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.example.GlobalConstants.*;

public class DrawBlock extends JPanel {
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