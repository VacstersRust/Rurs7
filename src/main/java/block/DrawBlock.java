package block;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DrawBlock extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultXYDataset dataset;
    private float lineThickness = 1.8f;
    private Map<String, String[][]> dataSetMap = new HashMap<>(); // Инициализируем dataSetMap здесь
    public String[][] currentData;

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

    public void putData(String[][] data) {
        clearGraph();
        String dataType = data[0][0].trim();
        currentData = data;
        clearGraph();
        switch (dataType) {
            case "f0a":
                get_f0a();
                break;
            case "f1a":
                get_f1a();
                break;
            case "f2a":
                get_f2a();
                break;
            default:
                System.out.println("Unknown data type: " + dataType);
                break;
        }
    }

    private void get_f0a() {
        drawGraph(currentData, "name graph for f0a");
    }

    private void get_f1a() {
        drawGraph(currentData, "name graph for f1a");
    }

    private void get_f2a() {
        prepareDataSet(this, currentData);
        // Создание кнопки переключения
        JToggleButton toggleButton = new JToggleButton("Температура/Давление");
        toggleButton.setSelected(true);// Устанавливаем состояние "Температура" по умолчанию
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                clearGraph();
                createMap(this, null, currentData[1], currentData[2], currentData[3], dataSetMap);
            } else {
                clearGraph();
                createMap(this, null, currentData[2], currentData[1], currentData[3], dataSetMap);
            }

        });
        add(toggleButton, BorderLayout.SOUTH);
        toggleButton.setVisible(true);
    }





    // Выбор серии данных для мультиграфика
    public static Map<String, String[][]> prepareDataSet(DrawBlock instance, String[][] data) {
        Map<String, String[][]> dataSetMap = new HashMap<>();
        // 1 Температура
        // 2 Давление
        // 3 Вязкость

        return dataSetMap;
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


    public void clearGraph() {
        dataset = new DefaultXYDataset();
        chart.getXYPlot().setDataset(dataset);
        repaint();
    }
}
