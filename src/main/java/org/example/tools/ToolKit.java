package org.example.tools;

import java.awt.*;

import static org.example.GlobalConstants.*;

public class ToolKit {
    public static void getXYV() {

    }

    public static void drawAxes(Graphics2D g2, int width, int height) {
        g2.setColor(Color.BLUE);
        g2.drawLine(50, height - 50, width - 50, height - 50);
        g2.drawLine(50, height - 50, 50, 50);
    }


    public static void drawFunc(Graphics2D g2, int width, int height) {

// График
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

// Сетка
    public static void drawGrid(Graphics2D g2, int width, int height) {
        // Сетка
        int xScale = (width + 100) / (xValues.size() - 1);
        int yScale = (height + 100) / (yValues.size() - 1);

        // Вертикальные линии сетки
        for (int i = 0; i < xValues.size(); i += 20) {
            int x = 50 + i * xScale;
            g2.drawLine(x, height - 50, x, 50);
            g2.setColor(Color.RED);
            g2.drawString(String.valueOf(xValues.get(i)), x - LABEL_PADDING, height - 30);
        }

        // Горизонтальные линии сетки
        for (int i = 0; i < yValues.size(); i += 20) {
            int y = height - 50 - i * yScale;
            g2.drawLine(50, y, width - 50, y);
            g2.setColor(Color.YELLOW);
            g2.drawString(String.valueOf(yValues.get(i)), 25, y + LABEL_PADDING);
        }
    }



    // Легенда
    public static void drawLgnd(Graphics2D g2, int width, int height) {
        g2.drawString("x", width - 20, height - 40);
        g2.drawString("y", 20, 20);
    }
}
