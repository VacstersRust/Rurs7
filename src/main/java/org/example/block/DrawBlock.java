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
    private int startX, endX;
    private int startY, endY;
    int StartMathX = 0;
    int StartMathY = 0;
    int EndMathX = 100;
    int EndMathY = 100;

    public DrawBlock() {
        setBackground(Color.WHITE);
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
                }
                if (EndMathX == 0){
                    StartMathX = 0;
                    StartMathY = 0;
                    EndMathX = 100;
                    EndMathY = 100;
                }

                int Start_XGrid_index = (startX-50) * (Ngrid) / (getWidth()-100)+1;
                int Start_YGrid_index = (getHeight() - startY - 50) * (Ngrid) / (getHeight()-100)+1;
                int End_XGrid_index = (endX-50) * (Ngrid) / (getWidth()-100)+1;
                int End_YGrid_index = (getHeight() - endY - 50) * (Ngrid) / (getHeight()-100)+1;

                int Start_X = ((int) ((Start_XGrid_index-1) * ((float)(EndMathX-StartMathX) / Ngrid)));
                int End_X   = (End_XGrid_index * ((EndMathX-StartMathX) / Ngrid));
                int Start_Y = ((int) ((Start_YGrid_index-1) * ((float)(EndMathY-StartMathY) / Ngrid)));
                int End_Y   = ((End_YGrid_index) * ((EndMathY-StartMathY) / Ngrid));

                StartMathX = Start_X;
                StartMathY = Start_Y;
                EndMathX = End_X;
                EndMathY = End_Y;
                paintComponent(getGraphics(), Start_X, End_X, Start_Y, End_Y);
            }
        });
    }


    // Отрисовка графика
    protected void paintComponent(Graphics g, int StartMathX, int EndMathX, int StartMathY, int EndMathY) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        ToolKit.drawGrid(g2, width, height);
        ToolKit.drawFunc(g2, width, height, StartMathX, EndMathX, StartMathY, EndMathY);
        ToolKit.drawLgnd(g2, width, height, StartMathX, EndMathX, StartMathY, EndMathY);
        ToolKit.drawAxes(g2, width, height);
    }
}