package dto;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

public class GraphPointSeries {
    private PointMetadata pointMetadata;
    private XYSeriesCollection seriesCollection;

    public GraphPointSeries(PointMetadata pointMetadata, XYSeriesCollection seriesCollection) {
        this.pointMetadata = pointMetadata;
        this.seriesCollection = seriesCollection;
    }

    public PointMetadata getPointMetadata() {
        return pointMetadata;
    }

    public void setDataType(PointMetadata pointMetadata) {
        this.pointMetadata = pointMetadata;
    }

    public XYSeriesCollection getSeriesCollection() {
        return seriesCollection;
    }

    public void setSeriesCollection(XYSeriesCollection simple2DPoints) {
        this.seriesCollection = simple2DPoints;
    }
}
