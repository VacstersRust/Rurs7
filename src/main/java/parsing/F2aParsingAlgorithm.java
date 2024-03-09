package parsing;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.time.LocalDateTime;
import java.util.*;

public class F2aParsingAlgorithm implements ParsingAlgorithm {

    private final DataType DATA_TYPE = DataType.F2A;
    public List<XYSeriesCollection> parse(String data) {
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

        Map<String, List<XYDataItem>> tPoints = new HashMap<>();

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeriesCollection tySeriesCollection = new XYSeriesCollection();
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


        // Добавляем данные для последней температуры, если они есть
        if (!temperatures.isEmpty()) {
            for (int i =0; i < temperatures.size(); i++)
            {
                XYDataItem item = new XYDataItem(
                        Double.parseDouble(pressures.get(i)),
                        Double.parseDouble(viscosities.get(i))
                );
                if (tPoints.containsKey(temperatures.get(i))) {
                    tPoints.get(temperatures.get(i)).add(item);
                }
                else {
                    ArrayList<XYDataItem> itemArrayList = new ArrayList<>();
                    itemArrayList.add(item);
                    tPoints.put(temperatures.get(i), itemArrayList);
                }
            }

            for (String key : tPoints.keySet()) {
                XYSeries series = new XYSeries(key);
                for (XYDataItem item : tPoints.get(key)) {
                    series.add(item);
                }
                xySeriesCollection.addSeries(series);
            }

            resultData[1] = temperatures.toArray(new String[0]);
            resultData[2] = pressures.toArray(new String[0]);
            resultData[3] = viscosities.toArray(new String[0]);

        }

        ArrayList<XYSeriesCollection> collectionArrayList = new ArrayList<>();
        collectionArrayList.add(xySeriesCollection);
        collectionArrayList.add(tySeriesCollection);

        // Возвращаем результат
        return collectionArrayList;
    }

    @Override
    public boolean canProcessThisType(DataType dataType) {
        return this.DATA_TYPE.equals(dataType);
    }
}
