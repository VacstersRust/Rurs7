package dto;

import parsing.DataType;

public class GraphPoint {
    private PointMetadata pointMetadata;
    private Simple2DPoint[] simple2DPoints;

    public GraphPoint(PointMetadata pointMetadata, Simple2DPoint[] simple2DPoints) {
        this.pointMetadata = pointMetadata;
        this.simple2DPoints = simple2DPoints;
    }

    public PointMetadata getPointMetadata() {
        return pointMetadata;
    }

    public void setDataType(PointMetadata pointMetadata) {
        this.pointMetadata = pointMetadata;
    }

    public Simple2DPoint[] getSimple2DPoints() {
        return simple2DPoints;
    }

    public void setSimple2DPoints(Simple2DPoint[] simple2DPoints) {
        this.simple2DPoints = simple2DPoints;
    }
}
