import org.jfree.chart.ChartPanel;

public class Renderer {
    private void setRender(ChartPanel panel){
        panel.getChart().getXYPlot().setRenderer(userRenderer);
    }
}
