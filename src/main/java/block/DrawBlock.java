package block;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;




public class DrawBlock extends JPanel {
    private DefaultXYDataset dataset; // Сохраняем ссылку на объект данных

    public DrawBlock() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        dataset = new DefaultXYDataset(); // Инициализация набора данных
        JFreeChart chart = ChartFactory.createXYLineChart(
                "File Data Chart", // Заголовок графика
                "X", // Название оси X
                "Y", // Название оси Y
                dataset, // Данные
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.getXYPlot().setDomainPannable(true);
        chart.getXYPlot().setRangePannable(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        add(chartPanel, BorderLayout.CENTER);
    }

    public void drawGraph(double[][] data) {
        if (dataset != null) {
            dataset.addSeries("Data", data); // Используем переданные данные для построения графика
            repaint(); // Перерисовываем график
        }
    }
}
