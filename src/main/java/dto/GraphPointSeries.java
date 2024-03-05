package dto;

import java.util.List;

public class GraphPointSeries {
    private PointMetadata pointMetadata;
    private List<Simple2DPoint[]> simple2DPointsSeries;

    public GraphPointSeries(PointMetadata pointMetadata, List<Simple2DPoint[]> simple2DPointsSeries) {
        this.pointMetadata = pointMetadata;
        this.simple2DPointsSeries = simple2DPointsSeries;
    }

    public PointMetadata getPointMetadata() {
        return pointMetadata;
    }

    public void setDataType(PointMetadata pointMetadata) {
        this.pointMetadata = pointMetadata;
    }

    public List<Simple2DPoint[]> getSimple2DPoints() {
        return simple2DPointsSeries;
    }

    public void setSimple2DPoints(List<Simple2DPoint[]> simple2DPoints) {
        this.simple2DPointsSeries = simple2DPoints;
    }
}
