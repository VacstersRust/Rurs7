package block;

import dto.GraphPointSeries;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import parsing.DataType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DrawBlock extends JPanel {
    private CoordinatePanel coordinatePanel;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultXYDataset dataset;
    private float lineThickness = 1.8f;
    private JToggleButton toggleButton;
    private Map<String, String[][]> dataSetMap = new HashMap<>(); // Инициализируем dataSetMap здесь
    public String[][] currentData;

    public DrawBlock() {
        dataset = new DefaultXYDataset();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        chart = ChartFactory.createXYLineChart(
                "File Data Chart",
                "X",
                "Y",
                null,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        XYPlot plot = chart.getXYPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);

        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Добавляем слушателя событий мыши
        chartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Показываем координаты при клике
                showCoordinates(e.getX(), e.getY());
                // Добавляем точку на график
                addPointToChart(e.getX(), e.getY());
            }
        });


        add(chartPanel, BorderLayout.CENTER);
    }

    private void showCoordinates(int x, int y) {
        // Создаем окно с координатами
        JOptionPane.showMessageDialog(this, "X: " + x + ", Y: " + y, "Coordinates", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addPointToChart(double x, double y) {
        // Добавляем точку на график
        dataset.addSeries("Point", new double[][]{{x}, {y}});
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);
        renderer.setSeriesLinesVisible(0, false); // Отключаем отображение линий для новой серии данных
        plot.setRenderer(renderer);
        repaint();
    }

    public void putData(GraphPointSeries data) {

//        // Создание слоя
//        JLayeredPane layeredPane = new JLayeredPane();
//        layeredPane.setPreferredSize(new Dimension(800, 600));
//
//        // Добавление графика на слой
//        layeredPane.add(chartPanel, JLayeredPane.DEFAULT_LAYER);
//
//        // Создание и добавление окна с координатами на слой
//        JFrame coordinateFrame = new JFrame("Coordinates");
//        coordinateFrame.add(coordinatePanel); // coordinatePanel - панель с отображением координат
//        layeredPane.add(coordinateFrame.getContentPane(), JLayeredPane.PALETTE_LAYER);
//
//        // Отображение слоя
//        add(layeredPane, BorderLayout.CENTER);
//
//        coordinatePanel = new CoordinatePanel(); // Создание coordinatePanel
//        add(coordinatePanel, BorderLayout.NORTH); // Добавление coordinatePanel в верхнюю часть DrawBlock


        // ++++++++

        // Очистка графика
        try {
            remove(toggleButton);
        }
        catch (NullPointerException ignored) {}
        clearGraph();
        DataType dataType = data.getPointMetadata().getDataType();

/*        switch (dataType) {
            case F0A:
                get_f0a();
                break;
            case F1A:
                get_f1a();
                break;
            case F2A:
                get_f2a();
                break;
            default:
                System.out.println("Unknown data type: " + dataType);
                break;
        }*/

        drawXyGraph(data.getSeriesCollection());
    }
// createMap(name, x, y);
    private void get_f0a() {
        createMap(currentData[1], currentData[2], currentData[3], dataSetMap);
    }

    private void get_f1a() {
        String dataxy[][] = {currentData[1], currentData[2]};
        drawGraph(dataxy, "Density");
    }

    private void get_f2a() {
        toggleButton = createToggleButton();

    }


private JToggleButton createToggleButton() {
        JToggleButton toggleButton = new JToggleButton("Температура/Давление");
        toggleButton.setSelected(false);
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                clearGraph();
                toggleButton.setLabel("Температура");
                createMap(currentData[1], currentData[2], currentData[3], dataSetMap);
            } else {
                clearGraph();
                toggleButton.setLabel("Температура");
                createMap(currentData[2], currentData[1], currentData[3], dataSetMap);
            }
        });
        add(toggleButton, BorderLayout.SOUTH);
        toggleButton.doClick();
        toggleButton.setVisible(true);
        return toggleButton;
    }


    private double[][] extractXYFromDataSet(String[][] dataSet) {
        int dataSize = dataSet.length;
        double[][] result = new double[2][dataSize];

        for (int i = 0; i < dataSet.length; i++) {
            try {
                result[0][i] = Double.parseDouble(dataSet[i][0]); // x value (температура)
                result[1][i] = Double.parseDouble(dataSet[i][1]); // y value (вязкость)
            } catch (NumberFormatException e) {
                // Обработка ошибок при парсинге
                result[i][0] = Double.NaN; //  значение по умолчанию при ошибке
                result[i][1] = Double.NaN; //  значение по умолчанию при ошибке
            }
        }
        for (double[] row : result) {
            Arrays.sort(row);
        }
        return result;
    }

    // Переделывает data общий в данные для графика
    private void createMap(String[] keyArray, String[] value1Array, String[] value2Array, Map<String, String[][]> dataSetMap) {
        Map<String, Map<String, String>> mapData = new HashMap<>();

        for (int i = 0; i < keyArray.length; i++) {
            String key = keyArray[i];
            String value1 = value1Array[i];
            String value2 = value2Array[i];

            if (!mapData.containsKey(key)) {
                mapData.put(key, new HashMap<>());
            }

            mapData.get(key).put(value1, value2);
        }

        for (Map.Entry<String, Map<String, String>> entry : mapData.entrySet()) {
            String keyValue = entry.getKey();
            Map<String, String> valueMap = entry.getValue();
            String[][] valueArray = new String[valueMap.size()][2];

            int idx = 0;
            for (Map.Entry<String, String> valueEntry : valueMap.entrySet()) {
                valueArray[idx][0] = valueEntry.getKey();
                valueArray[idx][1] = valueEntry.getValue();
                idx++;
            }
            drawGraph(valueArray, keyValue);
            dataSetMap.put(keyValue, valueArray);
        }
    }

    // Отрисовка графика по х у
    public void drawGraph(String[][] data, String graph_name) {
        double[][] xyData = extractXYFromDataSet(data);

        dataset.addSeries(graph_name, xyData);
        chart.getXYPlot().setDataset(dataset);
        XYItemRenderer renderer = chart.getXYPlot().getRenderer();
        BasicStroke stroke = new BasicStroke(lineThickness);

        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, stroke);
        }
        repaint();
    }

    public void drawXyGraph(XYSeriesCollection xySeriesCollection) {
        chart.getXYPlot().setDataset(xySeriesCollection);
        XYItemRenderer renderer = chart.getXYPlot().getRenderer();
        BasicStroke stroke = new BasicStroke(lineThickness);

        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, stroke);
        }
        repaint();
    }


    public void clearGraph() {
        dataset = new DefaultXYDataset();
        chart.getXYPlot().setDataset(dataset);
        repaint();
    }
}


