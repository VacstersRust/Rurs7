package parsing;

import dto.GraphPointSeries;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.awt.geom.Point2D;

public interface ParsingAlgorithm {

    GraphPointSeries parse(String data);

    boolean canProcessThisType(DataType dataType);

}
