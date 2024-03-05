import org.jfree.chart.ChartPanel;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Custom component to display coordinate info and close button
public class CoordinateInfoWindow extends JPanel {
    private XYTextAnnotation annotation;
    private DefaultXYDataset userPointDataset;
    private XYLineAndShapeRenderer userRenderer;
    private JPanel coordinatePanel; // Добавляем поле для хранения ссылки на родительскую панель
    private JLabel label;
    private JButton closeButton;
    private Container parent;
    private DefaultXYDataset s;
    private int index;
    ChartPanel mainPanel;


    public CoordinateInfoWindow(double xCoordinate, double yCoordinate, ChartPanel mainPanel, JPanel coordinatePanel,
                                DefaultXYDataset userPointDataset, int index) {
        this.coordinatePanel = coordinatePanel;
        this.userPointDataset = userPointDataset;
        this.mainPanel = mainPanel;
        this.index = index;

        addIndependentPointToChart(xCoordinate, yCoordinate);
        createLabel(xCoordinate, yCoordinate);
        selectPoint();
        createCloseButton();
        createStyle();
    }

    private void addIndependentPointToChart(double x, double y) {
        userPointDataset.addSeries(index, new double[][]{{x}, {y}});
        mainPanel.getChart().getXYPlot().setDataset(1, userPointDataset);
    }


    // Метод для создания стиля одной записи
    private void createStyle() {
        userRenderer = new XYLineAndShapeRenderer(true, true);
        userRenderer.setSeriesPaint(index, Color.blue);
        userRenderer.setAutoPopulateSeriesShape(false);
        userRenderer.setAutoPopulateSeriesPaint(false);

        // Настройка фоновой панели
        coordinatePanel.setBackground(new Color(200, 200, 200, 200)); // Полупрозрачный серый цвет
        coordinatePanel.setBorder(BorderFactory.createLineBorder(new Color(125, 125, 125, 150), 2));
        mainPanel.getChart().getXYPlot().setRenderer(userPointDataset.indexOf(index), userRenderer);


        // Настройка панели с надписями
        this.setBackground(new Color(230, 230, 230)); // Светло-серый цвет
        this.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200, 200), 5));
    }

    // Метод для создания подписи
    private void createLabel(double xCoordinate, double yCoordinate) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Используем вертикальное расположение компонентов
        JLabel label = new JLabel("Точка : " + index);
        label.setForeground(Color.blue); // Устанавливаем цвет текста
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Выравниваем по центру
        add(label);

        // Добавляем отступ между компонентами
        add(Box.createRigidArea(new Dimension(0, 5)));

        label = new JLabel("X: " + xCoordinate);
        label.setForeground(Color.black); // Устанавливаем цвет текста
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Выравниваем по центру
        add(label);

        // Добавляем отступ между компонентами
        add(Box.createRigidArea(new Dimension(0, 5)));

        label = new JLabel("Y: " + yCoordinate);
        label.setForeground(Color.black); // Устанавливаем цвет текста
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Выравниваем по центру
        add(label);

        annotation = new XYTextAnnotation("Точка " + index, xCoordinate, yCoordinate + 0.2);
        annotation.setTextAnchor(TextAnchor.BOTTOM_LEFT); // Устанавливаем якорь текста
        mainPanel.getChart().getXYPlot().addAnnotation(annotation);
    }


    // Метод для создания кнопки Close
    private void createCloseButton() {
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            if (coordinatePanel != null) {
                userPointDataset.removeSeries(index);
                coordinatePanel.remove(this);
                coordinatePanel.revalidate();
                mainPanel.getChart().getXYPlot().removeAnnotation(annotation);
            }
        });
        add(closeButton);
    }

    private void selectPoint() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userRenderer.setSeriesPaint(userPointDataset.indexOf(index), Color.red);
            }
        });
    }
}