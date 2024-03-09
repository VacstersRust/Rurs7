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

        Map<String, List<XYDataItem>> tPoints = new TreeMap<>();
        Map<String, List<XYDataItem>> pPoints = new TreeMap<>();

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
                XYDataItem pvItem = new XYDataItem(
                        Double.parseDouble(pressures.get(i)),
                        Double.parseDouble(viscosities.get(i))
                );
                if (tPoints.containsKey(temperatures.get(i))) {
                    tPoints.get(temperatures.get(i)).add(pvItem);
                }
                else {
                    ArrayList<XYDataItem> itemArrayList = new ArrayList<>();
                    itemArrayList.add(pvItem);
                    tPoints.put(temperatures.get(i), itemArrayList);
                }

                XYDataItem tvItem = new XYDataItem(
                        Double.parseDouble(temperatures.get(i)),
                        Double.parseDouble(viscosities.get(i))
                );


                if (pPoints.containsKey(pressures.get(i))) {
                    pPoints.get(pressures.get(i)).add(tvItem);
                }
                else {
                    ArrayList<XYDataItem> itemArrayList = new ArrayList<>();
                    itemArrayList.add(tvItem);
                    pPoints.put(pressures.get(i), itemArrayList);
                }


            }

            for (String key : tPoints.keySet()) {
                XYSeries pvSeries = new XYSeries(key);
                for (XYDataItem item : tPoints.get(key)) {
                    pvSeries.add(item);

                }
                xySeriesCollection.addSeries(pvSeries);


            }

            for (String key : pPoints.keySet()) {
                XYSeries tvSeries = new XYSeries(key);
                for (XYDataItem item : pPoints.get(key)) {
                    tvSeries.add(item);

                }
                tySeriesCollection.addSeries(tvSeries);


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
