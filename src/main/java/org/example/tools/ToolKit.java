package org.example.tools;

import java.awt.*;

import static org.example.GlobalConstants.*;

public class ToolKit {
    public static void drawAxes(Graphics2D g2, int width, int height) {
        g2.setColor(AXES_COLOR);
        g2.drawLine(50, height - 50, width - 50, height - 50);
        g2.drawLine(50, height - 50, 50, 50);
    }


//     График
public static void drawFunc(Graphics2D g2, int width, int height, int xs0,int xs1) {
        g2.setColor(FUNC_COLOR);
        g2.setStroke(new BasicStroke(FUNCTION_THICKNESS));
        float x,y,x0,y0;


//    // Получаемые значения
//int startX = 0;
//int endX = 600;
//int startY = 0;
//int endY = 400;
////


//dScale = endX - startX;


        x0 = xs0;
        y0 = x0 * x0;

        for (int i = 0; i < Nrange; i++) {
            x = x0 + ( (xs1-xs0) / (float)Nrange );
            y = x * x;

            int xWin0 = (int)(x0 * (width - 100) / dScale) + 50;
            int xWin = (int)(x * (width - 100) / dScale) + 50;
            int yWin0 = (height - 50) - (int) (y0 * (height - 100) / dScale);
            int yWin = (height - 50) - (int) (y * (height - 100) / dScale);

// Отсечка верха функции:
//            if (yWin > 50){
//                50 = x * x
//                y = x *x
//                 y2 = 50
//                g2.drawLine(xWin0, yWin0, xWin, yWin);
//              break;
//            }

            g2.drawLine(xWin0, yWin0, xWin, yWin);
            x0 = x;
            y0 = y;
        }
    }


// Сетка
public static void drawGrid(Graphics2D g2, int width, int height) {
    // Вертикальные линии сетки
    float x = 0;
    float y = (height-50);

    for (int i = 0; i < Nrange+1; i++) {
        g2.setColor(GRID_COLOR);
        g2.drawLine((int)x + 50, height - 50, (int)x + 50, 50);
        g2.drawLine(50, (int)y, width - 50, (int)y);
        x = x + ((width-100) / Nrange);
        y = y - ((height-100) / Nrange);
    }
}


    // Легенда
    public static void drawLgnd(Graphics2D g2, int width, int height) {
        g2.drawString("x", width - 20, height - 40);
        g2.drawString("y", 20, 20);
        int xs0 = 0;
        int xs1 = 10;
        float gridx = 0;
        float gridy = (height-50);
//        N = 10;
                for (int i = 0; i < Nrange+1; i++) {
            g2.setColor(GRID_COLOR);
            g2.drawString(String.valueOf((i *(float)dScale / Nrange)), gridx - LABEL_PADDING + 50, height - 20);
            g2.drawString(String.valueOf((i *(float)dScale / Nrange)), 20,   gridy + LABEL_PADDING);

            gridx = gridx + ((width-100) / Nrange);
            gridy = gridy - ((height-100) / Nrange);
        }



// Значение сетки по функции
//        for (int i = 0; i < N+1; i++) {
//            g2.setColor(GRID_COLOR);
//            g2.drawString(String.valueOf(x0), gridx - LABEL_PADDING + 50, height - 20);
//            g2.drawString(String.valueOf(y0), 20, gridy + LABEL_PADDING);
//
//            gridx = gridx + ((width-100) / half);
//            gridy = gridy - ((height+100) / half);
//
//            for (int j = 0; j < (dScale / N) - 1; j++) {
//
//                x0 = x0 + ( (xs1-xs0) / N );
//                y0 = x0 * x0;
//            }
//        }
    }
}
