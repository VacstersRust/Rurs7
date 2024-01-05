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

public class DrawBlock extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultXYDataset dataset;
    private float lineThickness = 1.8f;

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

    }

    public void drawGraph( String[][] data) {
        dataset = new DefaultXYDataset();

        int seriesCount = 1;
            double[][] xyData = extractXYFromDataSet(data);
            dataset.addSeries("Data" + seriesCount++, xyData);


        chart.getXYPlot().setDataset(dataset);
        XYItemRenderer renderer = chart.getXYPlot().getRenderer();
        BasicStroke stroke = new BasicStroke(lineThickness);
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, stroke);
        }
        repaint();
    }

    private double[][] extractXYFromDataSet(String[][] dataSet) {
        String[][] xyStrings = {dataSet[1], dataSet[2]};
        return parseStringToDouble(xyStrings);
    }
    private double[][] parseStringToDouble(String[][] strings) {
        double[][] result = new double[strings.length][];
        for (int i = 0; i < strings.length; i++) {
            result[i] = new double[strings[i].length];
            for (int j = 0; j < strings[i].length; j++) {
                try {
                    result[i][j] = Double.parseDouble(strings[i][j]);
                } catch (NumberFormatException e) {
                    // Обработка ошибок при парсинге
                    result[i][j] = Double.NaN; // Или другое значение по умолчанию при ошибке
                }
            }
        }
        return result;
    }
}
