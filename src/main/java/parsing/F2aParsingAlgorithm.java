package parsing;

import dto.GraphPointSeries;
import dto.PointMetadata;
import dto.Simple2DPoint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class F2aParsingAlgorithm implements ParsingAlgorithm {

    private final DataType DATA_TYPE = DataType.F2A;
    public GraphPointSeries parse(String data) {
        String[][] resultData = new String[4][];

        List<String> temperatures = new ArrayList<>();
        List<String> pressures = new ArrayList<>();
        List<String> viscosities = new ArrayList<>();

        String[] lines = data.split("\n");
        String currentTemp = null;

        List<String> metadata = new ArrayList<>();
        // Записываем метаданные для графика в список metadata

        // Добавим пример метаданных
        metadata.add("f2a");
        metadata.add("Температура");
        metadata.add("Вязкость");
        metadata.add("Давление");
        PointMetadata pointMetadata = new PointMetadata(
                DataType.F2A,
                LocalDateTime.now(),
                "Вязкость",
                "Давление"
        );
        resultData[0] = metadata.toArray(new String[0]);

        for (String line : lines) {
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            String[] elements = line.trim().split("\\s+");

            if (elements.length == 1) {
                // Обновляем текущую температуру
                currentTemp = elements[0];
            } else if (elements.length == 2) {
                if (currentTemp != null) {
                    temperatures.add(currentTemp);
                    pressures.add(elements[0]);
                    viscosities.add(elements[1]);
                }
            }
        }

        Simple2DPoint[] xSeries = new Simple2DPoint[temperatures.size()];


        // Добавляем данные для последней температуры, если они есть
        if (!temperatures.isEmpty()) {
            resultData[1] = temperatures.toArray(new String[0]);
            resultData[2] = pressures.toArray(new String[0]);
            resultData[3] = viscosities.toArray(new String[0]);

        }




        // Возвращаем результат
        List<Simple2DPoint[]> points = new ArrayList<>();
        return new GraphPointSeries(pointMetadata, points);
    }

    @Override
    public boolean canProcessThisType(DataType dataType) {
        return this.DATA_TYPE.equals(dataType);
    }
}
