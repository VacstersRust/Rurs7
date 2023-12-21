/*
* Данный класс должен выполнять отрисовку основной визуальной части блока
* и вызывать методы отрисовки и дополнительные инструменты
* Отрисовка должна производится в следующем порядке:
1) Получение данных функции для графика (должны подаваться на вход конечной функции отрисовки)
2) Вывод графика
3) Ожидание выделения мышкой области ( получение новых границ построения графика)
4) Перерисовка графика и сетки
*
*/

package org.example.block;

import static org.example.GlobalConstants.*;
import org.example.tools.ToolKit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class DrawBlock extends JPanel {
    private boolean isSelecting = false;
    private ArrayList<Integer> xValues;
    private ArrayList<Integer> yValues;
    private double xMin = -10.0;
    private double xMax = 10.0;
    private double yMin = -10.0;
    private double yMax = 10.0;
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
            // Получение координат выделенной области
                // mousePressed
                if (e.getButton() == MouseEvent.BUTTON1) {
                    startX = e.getX();
                    startY = e.getY();
                    isSelecting = true;

                    if (startX < endX) {
                        startX = -endX;
                    }
                    if (startY < endY) {
                        startY = -endY;
                    }
                }
                // mouseReleased
                if (e.getButton() == MouseEvent.BUTTON1) {
                    endX = e.getX();
                    endY = e.getY();
                    isSelecting = false;

                    if (endX < startX) {
                        endX = -startX;
                    }
                    if (endY < startY) {
                        endY = -startY;
                    }

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

    // Отрисовка графика
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        ToolKit.drawGrid(g2, width, height);
        ToolKit.drawFunc(g2, width, height, a, b);
        ToolKit.drawLgnd(g2, width, height);
        ToolKit.drawAxes(g2, width, height);
    }
}