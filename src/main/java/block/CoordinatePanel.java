package block;

import javax.swing.*;
import java.awt.*;

public class CoordinatePanel extends JPanel {
    private JLabel xLabel;
    private JLabel yLabel;

    public CoordinatePanel() {
        setLayout(new GridLayout(2, 1)); // Устанавливаем сетку 2x1

        // Создаем метки для отображения координат
        xLabel = new JLabel("X:");
        yLabel = new JLabel("Y:");

        // Добавляем метки на панель
        add(xLabel);
        add(yLabel);
    }

    // Метод для установки значений координат
    public void setCoordinates(double x, double y) {
        xLabel.setText("X: " + x);
        yLabel.setText("Y: " + y);
    }
}
