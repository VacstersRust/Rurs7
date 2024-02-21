package block;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DrawBlock extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultXYDataset dataset;
    private float lineThickness = 1.8f;
    private Map<String, String[][]> dataSetMap = new HashMap<>(); // Инициализируем dataSetMap здесь
    private String currentDataSet = "температура"; // Переменная для отслеживания текущего набора данных



    public DrawBlock() {
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

        add(chartPanel, BorderLayout.CENTER);
    }

    public void setGraph(String[][] data) {
        clearGraph();
        currentDataSet = "температура"; // Установите текущий набор данных
        prepareDataSet(this, data, currentDataSet); // Передаем текущий набор данных
    }



    public static Map<String, String[][]> prepareDataSet(DrawBlock instance, String[][] data, String switchParam) {
        Map<String, String[][]> dataSetMap = new HashMap<>();
        // 1 Температура
        // 2 Давление
        // 3 Вязкость
        if ("температура".equalsIgnoreCase(switchParam)) {
            createMap(instance, data, data[1], data[2], data[3], dataSetMap);
        } else if ("давление".equalsIgnoreCase(switchParam)) {
            createMap(instance, data, data[2], data[1], data[3], dataSetMap);
        }
        return dataSetMap;
    }


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

    private static void createMap(DrawBlock instance, String[][] data, String[] keyArray, String[] value1Array, String[] value2Array, Map<String, String[][]> dataSetMap) {
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
            instance.drawGraph(valueArray, keyValue);
            dataSetMap.put(keyValue, valueArray);
        }
    }

    public void clearGraph() {
        dataset = new DefaultXYDataset();
        chart.getXYPlot().setDataset(dataset);
        repaint();
    }
}
