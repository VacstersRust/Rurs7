import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class graph extends JFrame {
    private DefaultXYDataset userPointDataset;
    private XYLineAndShapeRenderer userPointsRenderer;
    private XYSeries series;
    private JFreeChart chart;
    private ChartPanel panel;
    private int dx;
    private int dy;

    public graph(String title) {
        super(title);
        panel = createChartPanel();
        setContentPane(panel);
    }

    private ChartPanel createChartPanel() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        series = createDataset(); // Входная точка данных
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Chart Example",
                "X",
                "Y",
                dataset
        );
        // Пользовательские точки
        userPointDataset = new DefaultXYDataset();
        userPointsRenderer = new XYLineAndShapeRenderer(false, true); // Только отображение точек
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        chart.getXYPlot().setRenderer(renderer);

        // Добавление созданного графика в панели
        JPanel coordinatePanel = new JPanel();
        coordinatePanel.setLayout(new BoxLayout(coordinatePanel, BoxLayout.Y_AXIS));

        ChartPanel panel = new ChartPanel(chart);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Get the X coordinate where the mouse was clicked
                    double xCoordinate = panel.getChart().getXYPlot().getDomainAxis().java2DToValue(e.getX(), panel.getChartRenderingInfo().getPlotInfo().getDataArea(), panel.getChart().getXYPlot().getDomainAxisEdge());
                    double yCoordinate = calculateY(xCoordinate);
                    addIndependentPointToChart(xCoordinate, yCoordinate);

                    // Create and show mini window
                    CoordinateInfoWindow miniWindow = new CoordinateInfoWindow(xCoordinate, yCoordinate, coordinatePanel);
                    miniWindow.setBounds(e.getX(), e.getY(), miniWindow.getPreferredSize().width, miniWindow.getPreferredSize().height);
                    miniWindow.setVisible(true);


                    coordinatePanel.add(miniWindow, BorderLayout.EAST); // Для центрирования компонента
                    coordinatePanel.revalidate();
                }
            }
            private double calculateY(double xCoordinate) {
                return linearInterpolation(xCoordinate, findNearestPointIndex(xCoordinate));
            }
        });

        makePanelDraggable(coordinatePanel); // Делаем панель draggable
        panel.setLayout(new BorderLayout());
        panel.add(coordinatePanel, BorderLayout.EAST);
        return panel;
    }


    private void addIndependentPointToChart(double x, double y) {
        int indexDataSet = userPointDataset.getSeriesCount() + 1; // Индекс новой серии данных
        userPointDataset.addSeries("Point " + indexDataSet, new double[][]{{x}, {y}});
        panel.getChart().getXYPlot().setDataset(1, userPointDataset);
    }


    private XYSeries createDataset() {
        XYSeries series = new XYSeries("Data Series");
        series.add(1.0, 1.0);
        series.add(2.0, 4.0);
        series.add(3.0, 9);
        series.add(4.0, 16);
        series.add(5.0, 25);
        return series;
    }

    // Делаем панель draggable
    private void makePanelDraggable(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dx = e.getX();
                dy = e.getY();
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.setLocation(panel.getX() + e.getX() - dx, panel.getY() + e.getY() - dy);
            }
        });
    }

    private int findNearestPointIndex(double xClicked) {
        // Find the nearest points (previous and next) for the given X coordinate
        int index = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < series.getItemCount(); i++) {
            double x = series.getX(i).doubleValue();
            double distance = x - xClicked;
            if (distance > 0 && distance < minDistance) { // Рассматриваем только положительные расстояния (правые точки)
                minDistance = distance;
                index = i - 1; // Выбираем индекс соседней точки слева
            }
        }
        return index;
    }

    private double linearInterpolation(double xClicked, int index) {
        // Perform linear interpolation between two nearest points
        double x1 = series.getX(index).doubleValue();
        double y1 = series.getY(index).doubleValue();
        double x2 = series.getX(index + 1).doubleValue();
        double y2 = series.getY(index + 1).doubleValue();

        // Linear interpolation formula: y = y1 + (x - x1) * (y2 - y1) / (x2 - x1)
        return y1 + (xClicked - x1) * (y2 - y1) / (x2 - x1);
    }




    // Custom component to display coordinate info and close button
    public class CoordinateInfoWindow extends JPanel {
        private JPanel coordinatePanel; // Добавляем поле для хранения ссылки на родительскую панель
        private JLabel label;
        private JButton closeButton;
        private Container parent;

        public CoordinateInfoWindow(double xCoordinate, double yCoordinate, JPanel coordinatePanel) {
            this.coordinatePanel = coordinatePanel; // Сохраняем ссылку на родительскую панель
            // Вызов метода для создания кнопки
            createLabel(xCoordinate, yCoordinate);
            createCloseButton();
            createStyle();// Вызов стиля записи
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
                    coordinatePanel.remove(this); // Удаляем компонент из его родительской панели
                    coordinatePanel.repaint();
                    coordinatePanel.revalidate();
                }
            });
            add(closeButton);
        }
    }




    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            graph example = new graph("XY Chart Example");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}