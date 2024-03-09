package parsing;

import org.jfree.data.xy.XYSeriesCollection;

import java.awt.geom.Point2D;

public interface ParsingAlgorithm {

    XYSeriesCollection parse(String data);

    boolean canProcessThisType(DataType dataType);

}
