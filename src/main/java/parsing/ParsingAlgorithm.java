package parsing;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.awt.geom.Point2D;

public interface ParsingAlgorithm {

    String[][] parse(String data);

    boolean canProcessThisType(DataType dataType);

}
