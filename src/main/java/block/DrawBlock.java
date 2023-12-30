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
import java.util.List;

public class DrawBlock extends JPanel {
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private DefaultXYDataset dataset;
    private float lineThickness = 1.8f; // Переменная для хранения толщины линии

    public DrawBlock() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        chart = ChartFactory.createXYLineChart(
                "File Data Chart", // Заголовок графика
                "X", // Название оси X
                "Y", // Название оси Y
                null, // Пустой dataset
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

    public void setLineThickness(float thickness) {
        lineThickness = thickness;
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer renderer = plot.getRenderer();

        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, new BasicStroke(lineThickness));
        }
    }

    public void drawGraph(List<double[][]> data) {
        dataset = new DefaultXYDataset(); // Создаем новый dataset

        int seriesCount = 1;
        for (double[][] dataSet : data) {
            dataset.addSeries("Data" + seriesCount++, dataSet); // Используем переданные данные для построения графиков
        }

        chart.getXYPlot().setDataset(dataset); // Устанавливаем новый dataset для отображения новых графиков

        // Установка толщины линий для каждой серии
        XYItemRenderer renderer = chart.getXYPlot().getRenderer();
        BasicStroke stroke = new BasicStroke(lineThickness); // Устанавливаем толщину линии
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, stroke);
        }

        repaint(); // Перерисовываем график
    }
}
