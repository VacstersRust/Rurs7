import org.jfree.chart.ChartPanel;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// Custom component to display coordinate info and close button
public class CoordinateInfoWindow extends JPanel {
    Map<Integer, Integer> indexmap;
    private DefaultXYDataset userPointDataset;
    private XYLineAndShapeRenderer userPointsRenderer;
    private JPanel coordinatePanel; // Добавляем поле для хранения ссылки на родительскую панель
    private JLabel label;
    private JButton closeButton;
    private Container parent;
    private DefaultXYDataset s;
    ChartPanel mainPanel;
    private int index;


    public CoordinateInfoWindow(double xCoordinate, double yCoordinate, ChartPanel mainPanel, JPanel coordinatePanel,
                                DefaultXYDataset userPointDataset, Map<Integer, Integer> indexmap ) {
        this.indexmap = indexmap;
        this.mainPanel = mainPanel;
        // Пользовательские точки
        this.userPointDataset = userPointDataset;
        userPointsRenderer = new XYLineAndShapeRenderer(false, true); // Только отображение точек

        addIndependentPointToChart(xCoordinate, yCoordinate);
        this.coordinatePanel = coordinatePanel; // Сохраняем ссылку на родительскую панель
        // Вызов метода для создания кнопки
        createLabel(xCoordinate, yCoordinate);
        createCloseButton();
        createStyle();// Вызов стиля записи
    }

    private void addIndependentPointToChart(double x, double y) {
        index = userPointDataset.getSeriesCount() + 1; // Индекс новой серии данных
        indexmap.put(this.hashCode(), userPointDataset.getSeriesCount());
        userPointDataset.addSeries("Point " + index, new double[][]{{x}, {y}});
        mainPanel.getChart().getXYPlot().setDataset(1, userPointDataset);
    }


    // Метод для создания стиля одной записи
    private void createStyle() {
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    // Метод для создания подписи
    private void createLabel(double xCoordinate, double yCoordinate) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        label = new JLabel("X Coordinate: " + xCoordinate);
        label = new JLabel("Y Coordinate:" + yCoordinate);
        add(label);
    }

    // Метод для создания кнопки Close
    private void createCloseButton() {
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            if (coordinatePanel != null) {
                userPointDataset.removeSeries(userPointDataset.getSeriesKey(indexmap.get(this.hashCode())));
                indexmap.remove(this.hashCode());
                coordinatePanel.remove(this); // Удаляем компонент из его родительской панели
                coordinatePanel.revalidate();
            }
        });
        add(closeButton);
    }
}