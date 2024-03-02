package parsing;

import java.util.ArrayList;
import java.util.List;

public class F2aParsingAlgorithm implements ParsingAlgorithm {

    private final DataType DATA_TYPE = DataType.F2A;
    public String[][] parse(String data) {
        List<String[]> resultData = new ArrayList<>();

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
        resultData.add(metadata.toArray(new String[0]));

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

        // Добавляем данные для последней температуры, если они есть
        if (!temperatures.isEmpty()) {
            resultData.add(temperatures.toArray(new String[0]));
            resultData.add(pressures.toArray(new String[0]));
            resultData.add(viscosities.toArray(new String[0]));
        }

        // Преобразуем List в двумерный массив
        String[][] result = new String[resultData.size()][];
        for (int i = 0; i < resultData.size(); i++) {
            result[i] = resultData.get(i);
        }

        // Возвращаем результат
        return result.length > 0 ? result : null;
    }

    @Override
    public boolean canProcessThisType(DataType dataType) {
        return this.DATA_TYPE.equals(dataType);
    }
}
