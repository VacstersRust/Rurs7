package org.example.tools;

import java.awt.*;

import static org.example.GlobalConstants.*;

public class ToolKit {
    public static void drawAxes(Graphics2D g2, int width, int height) {
        g2.setColor(AXES_COLOR);
        g2.drawLine(50, height - 50, width - 50, height - 50);
        g2.drawLine(50, height - 50, 50, 50);
    }



    public static void drawFunc(Graphics2D g2,
                                int width, int height,
                                float StartMathX,
                                float EndMathX,
                                float StartMathY,
                                float EndMathY)
    {
        g2.setColor(FUNC_COLOR);
        g2.setStroke(new BasicStroke(FUNCTION_THICKNESS));
        float x0,x1,y0,y1, x, y;
        x0 = StartMathX;
        x1 = EndMathX;
        y0 = StartMathY;
        y1 = EndMathY;
        float new_x;
        float new_y;
        x = x0;
        y = x*x;
        for (int i = 0; i < COUNT_POINTS; i ++)
            {
            new_x = x + (x1-x0)/COUNT_POINTS;
            new_y = new_x * new_x;

            int xWin0   = 50 + (int)(((width - 100) / (x1-x0)) * (x-x0));
            int xWin    = 50 + (int)(((width - 100) / (x1-x0)) * (new_x-x0));
            int yWin0   = (height - 50) - (int) ((height - 100) / (y1-y0) * (y-y0));
            int yWin    = (height - 50) - (int) ((height - 100) / (y1-y0) * (new_y-y0));
            x = new_x;
            y = new_y;
            g2.drawLine(xWin0, yWin0, xWin, yWin);
            }
    }

// Сетка
public static void drawGrid(Graphics2D g2, int width, int height) {
    float x =  0;
    float y = 0;

    for (int i = 0; i <= Ngrid; i++) {
        g2.setColor(GRID_COLOR);
        g2.drawLine((int)x + 50, height - 50, (int)x + 50, 50);
        g2.drawLine(50, height-50+ (int)y, width - 50, height-50+(int)y);
        x = x + ((width-100) / Ngrid);
        y = y - ((height-100) / Ngrid);
    }
}


    // Легенда
    public static void drawLgnd(Graphics2D g2,
                                int width, int height,
                                float StartMathX,
                                float EndMathX,
                                float StartMathY,
                                float EndMathY
    )
    {
        g2.drawString("x", width - 20, height - 40);
        g2.drawString("y", 20, 20);

        int gridx = 0;
        int gridy = (height-50);

        float gx=StartMathX;
        float gy=StartMathY;

                for (int i = 0; i <= Ngrid; i++) {
            g2.setColor(GRID_COLOR);
            g2.drawString(String.valueOf(gx), gridx - LABEL_PADDING + 50, height - 20);
            g2.drawString(String.valueOf(gy), 20,   gridy + LABEL_PADDING);
//            g2.drawString(String.valueOf(a + (i *(float)dScale / (Nrange * Nscale))), gridx - LABEL_PADDING + 50, height - 20);

            // Координаты на экране
            gridx = gridx + ((width-100) / Ngrid);
            gridy = gridy - ((height-100) / Ngrid);

            // Значения сетки
            gx = (gx + ( (float)(EndMathX - StartMathX) / Ngrid));
            gy = (gy + ( (float)(EndMathY - StartMathY) / Ngrid));
        }
    }
}

//
////     График
//public static void drawFunc(Graphics2D g2, int width, int height, int xs0,int xs1) {
//        g2.setColor(FUNC_COLOR);
//        g2.setStroke(new BasicStroke(FUNCTION_THICKNESS));
//        float x,y,x0,y0;
//
////    // Получаемые значения
////int startX = 0;
////int endX = 600;
////int startY = 0;
////int endY = 400;
//////
//
////dScale = endX - startX;
//
//        x0 = xs0;
//        y0 = x0 * x0;
//
//        ;
//
//        for (int i = 0; i < Nrange; i++) {
//            x = x0 + ( (xs1-xs0) / (float)Nrange );
//            y = x * x;
//
//            int xWin0 = (int)((x0-a)* (width - 100) / dScale) + 50;
//            int xWin = (int)((x-a) * (width - 100) / dScale) + 50;
//            int yWin0 = (height - 50) - (int) (y0 * (height - 100) / dScale);
//            int yWin = (height - 50) - (int) (y * (height - 100) / dScale);
//
//
//
//
//// Отсечка верха функции:
////            if (yWin > 50){
////                50 = x * x
////                y = x *x
////                 y2 = 50
////                g2.drawLine(xWin0, yWin0, xWin, yWin);
////              break;
////            }
//
//            g2.drawLine(xWin0, yWin0, xWin, yWin);
//            x0 = x;
//            y0 = y;
//        }
//    }