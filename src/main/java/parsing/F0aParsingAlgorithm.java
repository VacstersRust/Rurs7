package parsing;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class F0aParsingAlgorithm implements ParsingAlgorithm {

    private final DataType DATA_TYPE = DataType.F0A;
    @Override
    public List<XYSeriesCollection> parse(String data) {
        Map<String, List<Map<String, String>>> parameters = parseF0AData(data);


        String[] paramArray = parameters.keySet().toArray(new String[0]);
        String selectedParam = (String) JOptionPane.showInputDialog(null,
                "Выберите параметр для построения графика:",
                "Выбор параметра",
                JOptionPane.QUESTION_MESSAGE,
                null,
                paramArray,
                paramArray[0]);


        if (selectedParam != null) {
            List<Map<String, String>> variables = parameters.get(selectedParam);
            int countPoints = 25;
            double P = 0;
            double T = 0;
            double[][] parsedData = new double[4][countPoints];


            double P0_value = Double.parseDouble(JOptionPane.showInputDialog(null, "Введите P0"));
            double P1_value = Double.parseDouble(JOptionPane.showInputDialog(null, "Введите P"));
            double T0_value = Double.parseDouble(JOptionPane.showInputDialog(null, "Введите T0"));
            double T1_value = Double.parseDouble(JOptionPane.showInputDialog(null, "Введите T"));


            for (int i = 0; i < countPoints; i++) {
                double y = 0;
                for (Map<String, String> variableMap : variables) {
                    for (Map.Entry<String, String> entry : variableMap.entrySet()) {
                        y = y + Math.pow(T, Integer.parseInt(variableMap.get("P_index"))) *
                                Math.pow(P, Integer.parseInt(variableMap.get("T_index")));
                    }
                }
                parsedData[0][i] = i;
                parsedData[1][i] = P;
                parsedData[2][i] = T;
                parsedData[3][i] = y;

                P = P0_value + (P1_value - P0_value) * ((double) i / (countPoints));
                T = T0_value + (T1_value - T0_value) * ((double) i / (countPoints));
            }


            String[][] resultData = new String[4][countPoints];

            XYSeries pSer = new XYSeries("P_series");
            XYSeries tSer = new XYSeries("T_series");

            for (int i = 0; i < countPoints; i++) {
                pSer.add(parsedData[1][i], parsedData[3][i]);
                tSer.add(parsedData[2][i], parsedData[3][i]);
            }
            XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
            xySeriesCollection.addSeries(pSer);
            xySeriesCollection.addSeries(tSer);

            ArrayList<XYSeriesCollection> collectionArrayList = new ArrayList<>();
            collectionArrayList.add(xySeriesCollection);

            return collectionArrayList;
        }
        return null;
    }

    private static Map<String, List<Map<String, String>>> parseF0AData(String data) {
        String[] lines = data.split("\\n");
        Map<String, List<Map<String, String>>> parameters = new LinkedHashMap<>();
        List<Map<String, String>> paramVariables = new ArrayList<>();
        String currentParam = null;


        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("#")) {
                if (currentParam != null && !paramVariables.isEmpty()) {
                    parameters.put(currentParam, new ArrayList<>(paramVariables));
                    paramVariables.clear();
                }
                currentParam = line.substring(1).trim();
            } else if (!line.isEmpty()) {
                Map<String, String> variableMap = new LinkedHashMap<>();
                double[] parsedValues = F0A_ParseString(line);

                variableMap.put("Value", String.valueOf(parsedValues[0]));
                variableMap.put("P_index", String.valueOf((int) parsedValues[1]));
                variableMap.put("T_index", String.valueOf((int) parsedValues[2]));

                paramVariables.add(variableMap);
            }
        }
        if (currentParam != null && !paramVariables.isEmpty()) {
            parameters.put(currentParam, new ArrayList<>(paramVariables));
        }
        return parameters;
    }

    public static double[] F0A_ParseString(String line) {
        String[] elements = line.trim().split("\\s+");
        double[] result = new double[3];

        String value = elements[0]; // первый элемент - значение

        int pIndex = 0; // индекс для P по умолчанию
        int tIndex = 0; // индекс для T по умолчанию

        if (elements.length > 1) {
            String secondWord = elements[1];

            if (secondWord.contains("P")) {
                pIndex = extractIndex(secondWord, 'P');
            }
            if (secondWord.contains("T")) {
                tIndex = extractIndex(secondWord, 'T');
            }
        }

        result[0] = Double.parseDouble(value);
        result[1] = pIndex;
        result[2] = tIndex;

        return result;
    }

    public static int extractIndex(String element, char target) {
        int index = 0;
        int indexStart = element.indexOf(target) + 1; // начальный индекс для поиска цифры

        if (indexStart > 0 && indexStart < element.length() && Character.isDigit(element.charAt(indexStart))) {
            while (indexStart < element.length() && Character.isDigit(element.charAt(indexStart))) {
                indexStart++;
            }

            try {
                index = Integer.parseInt(element.substring(element.indexOf(target) + 1, indexStart));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            index = 1;
        }

        return index;
    }

    @Override
    public boolean canProcessThisType(DataType dataType) {
        return this.DATA_TYPE.equals(dataType);
    }
}
