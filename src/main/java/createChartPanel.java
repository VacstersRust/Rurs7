import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class createChartPanel {
    XYSeriesCollection userGraphDataset = new XYSeriesCollection();
    DefaultXYDataset userPointDataset = new DefaultXYDataset();
    private XYLineAndShapeRenderer userRenderer;

    public static void main() {

    }


    private ChartPanel userCreateChartPanel() {
        ChartPanel panel = addLKMListener(new ChartPanel(userCreateChart()));
        panel.setLayout(new BorderLayout());
        return panel;
    }


    private JPanel userCreateCoordinatePanel() {
        JPanel userCoordinatePanel = new JPanel();
        userCoordinatePanel.setLayout(new BoxLayout(userCoordinatePanel, BoxLayout.Y_AXIS));
        return userCoordinatePanel;
    }


    private ChartPanel addLKMListener(ChartPanel panel) {
        int[] id = {0};
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Get the X coordinate where the mouse was clicked
                    double xCoordinate = panel.getChart().getXYPlot().getDomainAxis().java2DToValue(e.getX(),
                            panel.getChartRenderingInfo().getPlotInfo().getDataArea(),
                            panel.getChart().getXYPlot().getDomainAxisEdge());
                    double yCoordinate = calculateY(xCoordinate);
                    id[0]++;
                    // Create and show mini window
                    CoordinateInfoWindow miniWindow = new CoordinateInfoWindow(
                            xCoordinate,
                            yCoordinate,
                            panel,
                            panel,
                            userPointDataset,
                            id[0]
                    );
                    miniWindow.setBounds(e.getX(), e.getY(), miniWindow.getPreferredSize().width, miniWindow.getPreferredSize().height);
                    miniWindow.setVisible(true);
                    panel.add(miniWindow, BorderLayout.EAST);
                    panel.revalidate();
                }
            }
            private double calculateY(double xCoordinate) {
                return linearInterpolation(xCoordinate, findNearestPointIndex(xCoordinate));
            }
        });
        return panel;
    }

    private static void addCoordinatePanel(JPanel panel, MouseEvent e) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    }

    private static JFreeChart userCreateChart() {
        JFreeChart chart = ChartFactory.createXYLineChart( // создаём пустой график
                "XY Chart Example",
                "X",
                "Y",
                null
        );
        userRenderChart(chart); // обновляем дефолтные параметры
        return chart;
    }

    private static void userRenderChart(JFreeChart chart) {
        XYLineAndShapeRenderer userRenderer;
        userRenderer = new XYLineAndShapeRenderer(true, true);
        userRenderer.setAutoPopulateSeriesShape(false);
        userRenderer.setAutoPopulateSeriesPaint(false);
        chart.getXYPlot().setRenderer(userRenderer);
    }


    public void userPutDataSet() {

    }
}
