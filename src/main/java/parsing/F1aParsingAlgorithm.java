package parsing;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.time.LocalDateTime;

public class F1aParsingAlgorithm implements ParsingAlgorithm {

    private final DataType DATA_TYPE = DataType.F1A;

    public XYSeriesCollection parse(String data) {
        String[] lines = data.split("\\n");
        int numberOfPoints = lines.length;
        String[][] parsedData = new String[3][numberOfPoints];

        XYSeries xySeries = new XYSeries("X_Series");
        for (String line : lines) {
            String[] values = line.split("\\s+");

            if (values.length == 2) {
                try {
                    xySeries.add(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
/*                parsedData[1][i] = values[0]; // x
                parsedData[2][i] = values[1]; // y*/
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        /*
        parsedData[0][0] = " f1a";
        parsedData[0][1] = "Икс";
        parsedData[0][2] = "Игрик"; */
        return new XYSeriesCollection(xySeries);
        }

    @Override
    public boolean canProcessThisType(DataType dataType) {
        return this.DATA_TYPE.equals(dataType);
    }
}