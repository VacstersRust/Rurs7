////// Создание интерполятора при запуске программы
////Interpolator interpolator = new Interpolator();
////
////// Добавление точек данных в интерполятор
////        interpolator.addPoint(x1, y1);
////        interpolator.addPoint(x2, y2);
////// добавление других точек...
////
////// Запрос значения в определенной точке
////        double x = 2.5; // Например, значение X, для которого вы хотите найти соответствующее значение Y
////        double interpolatedY = interpolator.interpolate(x);
////
////        // Создаем панель для мини-окна
////        JPanel miniWindowPanel = new JPanel();
////        miniWindowPanel.setLayout(new BoxLayout(miniWindowPanel, BoxLayout.Y_AXIS)); // Размещаем компоненты вертикально
////
////// Создаем мини-окно и добавляем его на панель
////        CoordinateInfoWindow miniWindow = new CoordinateInfoWindow(xCoordinate, calculateY(xCoordinate), e.getX(), e.getY(), panel);
////        miniWindowPanel.add(miniWindow);
////
////// Добавляем панель с мини-окном в правую часть основной панели
////        panel.add(miniWindowPanel, BorderLayout.EAST);
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.XYItemRenderer;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Ellipse2D;
//
//public class graph extends JFrame {
//    private XYSeries series;
//    private ChartPanel panel;
//
//
//    public graph(String title) {
//        super(title);
//
//        // Create chart panel
//        ChartPanel panel = createChartPanel();
//        setContentPane(panel);
//    }
//
//    private ChartPanel createChartPanel() {
//        // Create dataset
//        series = new XYSeries("Data Series"); // Используем поле класса, а не локальную переменную
//        series.add(1.0, 1.0);
//        series.add(2.0, 4.0);
//        series.add(3.0, 9);
//        series.add(4.0, 16);
//        series.add(5.0, 25);
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(series);
//
//        // Create chart
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "XY Chart Example",
//                "X",
//                "Y",
//                dataset
//        );
//
//        // Customize renderer to show points
//        XYPlot plot = chart.getXYPlot();
//        XYItemRenderer renderer = plot.getRenderer();
//        renderer.setSeriesPaint(0, Color.BLUE); // Set color of the points
//        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6)); // Set shape of the points
//
//        plot.setDomainCrosshairVisible(true); // Включить отображение вертикальной линии курсора
//        plot.setRangeCrosshairVisible(true);  // Включить отображение горизонтальной линии курсора
//        plot.setDomainCrosshairLockedOnData(false); // Закрепить вертикальный курсор за данными
//        plot.setRangeCrosshairLockedOnData(false); // Закрепить вертикальный курсор за данными
//
//
//// Настраиваем стиль вертикального курсора
//        plot.setDomainCrosshairPaint(Color.RED); // Цвет линии курсора
//        plot.setDomainCrosshairStroke(new BasicStroke(2)); // Толщина линии курсора
//        plot.setDomainCrosshairPaint(Color.BLUE); // Цвет метки курсора
//        plot.setDomainCrosshairPaint(new Color(255, 255, 255, 150)); // Фон метки курсора
//
//
//        // Create panel
//        ChartPanel panel = new ChartPanel(chart);
//
//        // Add mouse listener to the chart panel
//        panel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    // Get the X coordinate where the mouse was clicked
//                    double xCoordinate = panel.getChart().getXYPlot().getDomainAxis().java2DToValue(e.getX(), panel.getChartRenderingInfo().getPlotInfo().getDataArea(), panel.getChart().getXYPlot().getDomainAxisEdge());
//                    double yCoordinate = calculateY(xCoordinate);
//
//                    // Create and show mini window
//                    CoordinateInfoWindow miniWindow = new CoordinateInfoWindow(xCoordinate, calculateY(xCoordinate), e.getX(), e.getY(), panel);
//                    panel.add(miniWindow, BorderLayout.EAST); // Для центрирования компонента
//                    miniWindow.setBounds(e.getX(), e.getY(), miniWindow.getPreferredSize().width, miniWindow.getPreferredSize().height);
//                    miniWindow.setVisible(true);
//
//                    // Repaint the panel to reflect changes
//                    panel.revalidate();
//                    panel.repaint();
//                }
//            }
//
//            private double calculateY(double xCoordinate) {
//                return linearInterpolation(xCoordinate, findNearestPointIndex(xCoordinate));
//            }
//        });
//        return panel;
//    }
//
//    private int findNearestPointIndex(double xClicked) {
//        // Find the nearest points (previous and next) for the given X coordinate
//        int index = -1;
//        double minDistance = Double.MAX_VALUE;
//        for (int i = 0; i < series.getItemCount(); i++) {
//            double x = series.getX(i).doubleValue();
//            double distance = x - xClicked;
//            if (distance > 0 && distance < minDistance) { // Рассматриваем только положительные расстояния (правые точки)
//                minDistance = distance;
//                index = i - 1; // Выбираем индекс соседней точки слева
//            }
//        }
//        return index;
//    }
//
//    private double linearInterpolation(double xClicked, int index) {
//        // Perform linear interpolation between two nearest points
//        double x1 = series.getX(index).doubleValue();
//        double y1 = series.getY(index).doubleValue();
//        double x2 = series.getX(index + 1).doubleValue();
//        double y2 = series.getY(index + 1).doubleValue();
//
//        // Linear interpolation formula: y = y1 + (x - x1) * (y2 - y1) / (x2 - x1)
//        return y1 + (xClicked - x1) * (y2 - y1) / (x2 - x1);
//    }
//
//    // Custom component to display coordinate info and close button
//    public class CoordinateInfoWindow extends JPanel {
//        private JLabel label;
//        private JButton closeButton;
//        private int dx;
//        private int dy;
//        private Container parent;
//
//        public CoordinateInfoWindow(double xCoordinate, double yCoordinate, int x, int y, Container parent) {
//            this.parent = parent;
//            setLayout(new FlowLayout(FlowLayout.CENTER));
//            label = new JLabel("X Coordinate: " + xCoordinate);
//            label = new JLabel("Y Coordinate:" + yCoordinate);
//            add(label);
//
//            closeButton = new JButton("Close");
//            closeButton.addActionListener(e -> {
//                if (parent != null) {
//                    parent.remove(this);
//                    parent.repaint();
//                }
//            });
//            add(closeButton);
//
//            setBackground(Color.white);
//            setBorder(BorderFactory.createLineBorder(Color.black));
//
//            // Make the window draggable
//            addMouseListener(new MouseAdapter() {
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    dx = e.getX();
//                    dy = e.getY();
//                }
//            });
//            addMouseMotionListener(new MouseAdapter() {
//                @Override
//                public void mouseDragged(MouseEvent e) {
//                    setLocation(getX() + e.getX() - dx, getY() + e.getY() - dy);
//                }
//            });
//        }
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            graph example = new graph("XY Chart Example");
//            example.setSize(800, 400);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            example.setVisible(true);
//        });
//    }
//}


//// NEWWWW
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.XYItemRenderer;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Ellipse2D;
//
//public class GGraph extends JFrame {
//    private XYSeries series;
//    private ChartPanel panel;
//    private int dx;
//    private int dy;
//
////    public Graph(String title) {
//        super(title);
//
//        // Create chart panel
//        ChartPanel panel = createChartPanel();
//        setContentPane(panel);
//    }
//
//    private ChartPanel createChartPanel() {
//        // Create dataset
//        series = new XYSeries("Data Series"); // Используем поле класса, а не локальную переменную
//        series.add(1.0, 1.0);
//        series.add(2.0, 4.0);
//        series.add(3.0, 9);
//        series.add(4.0, 16);
//        series.add(5.0, 25);
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(series);
//
//        // Create chart
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "XY Chart Example",
//                "X",
//                "Y",
//                dataset
//        );
//
//        // Customize renderer to show points
//        XYPlot plot = chart.getXYPlot();
//        XYItemRenderer renderer = plot.getRenderer();
//        renderer.setSeriesPaint(0, Color.BLUE); // Set color of the points
//        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6)); // Set shape of the points
//
//        plot.setDomainCrosshairVisible(true); // Включить отображение вертикальной линии курсора
//        plot.setRangeCrosshairVisible(true);  // Включить отображение горизонтальной линии курсора
//        plot.setDomainCrosshairLockedOnData(false); // Закрепить вертикальный курсор за данными
//        plot.setRangeCrosshairLockedOnData(false); // Закрепить вертикальный курсор за данными
//
//
//// Настраиваем стиль вертикального курсора
//        plot.setDomainCrosshairPaint(Color.RED); // Цвет линии курсора
//        plot.setDomainCrosshairStroke(new BasicStroke(2)); // Толщина линии курсора
//        plot.setDomainCrosshairPaint(Color.BLUE); // Цвет метки курсора
//        plot.setDomainCrosshairPaint(new Color(255, 255, 255, 150)); // Фон метки курсора
//
//
//        // Create panel
//        ChartPanel panel = new ChartPanel(chart);
//
//        // Создаем панель для окон с координатами
//        JPanel coordinatePanel = new JPanel();
//        coordinatePanel.setLayout(new BoxLayout(coordinatePanel, BoxLayout.Y_AXIS));
//
//        // Add mouse listener to the chart panel
//        panel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    // Get the X coordinate where the mouse was clicked
//                    double xCoordinate = panel.getChart().getXYPlot().getDomainAxis().java2DToValue(e.getX(), panel.getChartRenderingInfo().getPlotInfo().getDataArea(), panel.getChart().getXYPlot().getDomainAxisEdge());
//                    double yCoordinate = calculateY(xCoordinate);
//
//                    // Create and show mini window
//                    CoordinateInfoWindow miniWindow = new CoordinateInfoWindow(xCoordinate, calculateY(xCoordinate), e.getX(), e.getY(), coordinatePanel);
//                    miniWindow.setBounds(e.getX(), e.getY(), miniWindow.getPreferredSize().width, miniWindow.getPreferredSize().height);
//                    miniWindow.setVisible(true);
//
//                    coordinatePanel.add(miniWindow, BorderLayout.EAST); // Для центрирования компонента
//
//                    // Repaint the panel to reflect changes
//                    coordinatePanel.revalidate();
////                    coordinatePanel.repaint();
//                }
//            }
//            private double calculateY(double xCoordinate) {
//                return linearInterpolation(xCoordinate, findNearestPointIndex(xCoordinate));
//            }
//        });
//
//        // Make the window draggable
//        coordinatePanel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                dx = e.getX();
//                dy = e.getY();
//            }
//        });
//        coordinatePanel.addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                coordinatePanel.setLocation(coordinatePanel.getX() + e.getX() - dx, coordinatePanel.getY() + e.getY() - dy);
//            }
//        });
//        panel.setLayout(new BorderLayout());
//        panel.add(coordinatePanel, BorderLayout.EAST);
//        return panel;
//    }
//
//    private int findNearestPointIndex(double xClicked) {
//        // Find the nearest points (previous and next) for the given X coordinate
//        int index = -1;
//        double minDistance = Double.MAX_VALUE;
//        for (int i = 0; i < series.getItemCount(); i++) {
//            double x = series.getX(i).doubleValue();
//            double distance = x - xClicked;
//            if (distance > 0 && distance < minDistance) { // Рассматриваем только положительные расстояния (правые точки)
//                minDistance = distance;
//                index = i - 1; // Выбираем индекс соседней точки слева
//            }
//        }
//        return index;
//    }
//
//    private double linearInterpolation(double xClicked, int index) {
//        // Perform linear interpolation between two nearest points
//        double x1 = series.getX(index).doubleValue();
//        double y1 = series.getY(index).doubleValue();
//        double x2 = series.getX(index + 1).doubleValue();
//        double y2 = series.getY(index + 1).doubleValue();
//
//        // Linear interpolation formula: y = y1 + (x - x1) * (y2 - y1) / (x2 - x1)
//        return y1 + (xClicked - x1) * (y2 - y1) / (x2 - x1);
//    }
//
//    // Custom component to display coordinate info and close button
//    public class CoordinateInfoWindow extends JPanel {
//        private JLabel label;
//        private JButton closeButton;
//        private int dx;
//        private int dy;
//        private Container parent;
//
//        public CoordinateInfoWindow(double xCoordinate, double yCoordinate, int x, int y, Container parent) {
//            this.parent = parent;
//            setLayout(new FlowLayout(FlowLayout.CENTER));
//            label = new JLabel("X Coordinate: " + xCoordinate);
//            label = new JLabel("Y Coordinate:" + yCoordinate);
//            add(label);
//
//            closeButton = new JButton("Close");
//            closeButton.addActionListener(e -> {
//                if (parent != null) {
//                    parent.remove(this);
//                    parent.repaint();
//                    parent.revalidate();
//                }
//            });
//            add(closeButton);
//
//            setBackground(Color.white);
//            setBorder(BorderFactory.createLineBorder(Color.black));
//
////            // Make the window draggable
////            addMouseListener(new MouseAdapter() {
////                @Override
////                public void mousePressed(MouseEvent e) {
////                    dx = e.getX();
////                    dy = e.getY();
////                }
////            });
////            addMouseMotionListener(new MouseAdapter() {
////                @Override
////                public void mouseDragged(MouseEvent e) {
////                    setLocation(getX() + e.getX() - dx, getY() + e.getY() - dy);
////                }
////            });
//        }
//
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            graph example = new graph("XY Chart Example");
//            example.setSize(800, 400);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            example.setVisible(true);
//        });
//    }
//}