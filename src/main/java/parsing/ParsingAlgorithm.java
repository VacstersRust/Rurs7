package parsing;

import org.jfree.data.xy.XYSeriesCollection;

import java.awt.geom.Point2D;
import java.util.List;

public interface ParsingAlgorithm {

    List<XYSeriesCollection> parse(String data);

    boolean canProcessThisType(DataType dataType);

}
