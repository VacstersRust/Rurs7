package parsing;

import dto.GraphPointSeries;
import dto.PointMetadata;
import dto.Simple2DPoint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class F1aParsingAlgorithm implements ParsingAlgorithm {

    private final DataType DATA_TYPE = DataType.F1A;

    public GraphPointSeries parse(String data) {
        String[] lines = data.split("\\n");
        int numberOfPoints = lines.length;
        String[][] parsedData = new String[3][numberOfPoints];

        Simple2DPoint[] xSeries = new Simple2DPoint[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            String[] values = lines[i].split("\\s+");

            if (values.length == 2) {
                try {
                    xSeries[i] = new Simple2DPoint(
                            Double.parseDouble(values[0]),
                            Double.parseDouble(values[1])
                    );
/*                parsedData[1][i] = values[0]; // x
                parsedData[2][i] = values[1]; // y*/
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        PointMetadata pointMetadata = new PointMetadata(
                DataType.F1A,
                LocalDateTime.now(),
                "Sample B",
                "John doe"

        );/*
        parsedData[0][0] = " f1a";
        parsedData[0][1] = "Икс";
        parsedData[0][2] = "Игрик"; */
        List<Simple2DPoint[]> points = new ArrayList<>();
        return new GraphPointSeries(pointMetadata, points);
        }

    @Override
    public boolean canProcessThisType(DataType dataType) {
        return this.DATA_TYPE.equals(dataType);
    }
}