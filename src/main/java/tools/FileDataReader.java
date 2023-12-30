package tools;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileDataReader {

    public static List<double[][]> readFileData(String filePath, String fileExtension) {
        try {
            StringBuilder content = new StringBuilder();
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            bufferedReader.close();

            // Обработка содержимого файла в зависимости от расширения
            return parseData(content.toString(), fileExtension);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<double[][]> parseData(String data, String fileExtension) {
        if (fileExtension != null) {
            if (fileExtension.equals("dat") || fileExtension.equals("f1a")) {
                List<double[][]> parsedDataList = new ArrayList<>();
                parsedDataList.add(parseDatOrF1A(data));
                return parsedDataList;
            } else if (fileExtension.equals("f0a")) {
                List<double[][]> parsedDataList = new ArrayList<>();
                parsedDataList.add(parseF0A(data));
                return parsedDataList;
            } else if (fileExtension.equals("f2a")) {
                return parseF2A(data);
            }
        }
        return null; // Обработка других типов файлов или ошибок
    }

    private static double[][] parseDatOrF1A(String data) {
        String[] lines = data.split("\\n");
        int numberOfPoints = lines.length;
        double[][] parsedData = new double[2][numberOfPoints];

        for (int i = 0; i < numberOfPoints; i++) {
            String[] values = lines[i].split("\\s+");

            if (values.length == 2) {
                try {
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);
                    parsedData[0][i] = x;
                    parsedData[1][i] = y;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return parsedData;
    }

    private static Map<String, List<Map<String, String>>> parseF0AData(String data) {
        String[] lines = data.split("\\n");
        Map<String, List<Map<String, String>>> parameters = new LinkedHashMap<>();
        String currentParam = null;
        List<Map<String, String>> paramVariables = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("#")) {
                if (currentParam != null && !paramVariables.isEmpty()) {
                    parameters.put(currentParam, paramVariables);
                    paramVariables = new ArrayList<>();
                }
                currentParam = line.substring(1).trim();
            } else if (!line.isEmpty()) {
                String[] parts = line.split("\\s+");

                if (parts.length >= 2) {
                    String variableValue = parts[0];
                    String variableName = parts[1];
                    Map<String, String> variableMap = new LinkedHashMap<>();
                    variableMap.put(variableName, variableValue);
                    paramVariables.add(variableMap);
                }
            }
        }
        if (currentParam != null && !paramVariables.isEmpty()) {
            parameters.put(currentParam, paramVariables);
        }
        return parameters;
    }

    public static double[][] parseF0A(String data) {
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

            // Введите x0 и x1, чтобы определить диапазон точек x
            double x0 = Double.parseDouble(JOptionPane.showInputDialog(null, "Введите начальное значение x:"));
            double x1 = Double.parseDouble(JOptionPane.showInputDialog(null, "Введите конечное значение x:"));
            int countPoints = 250; // Задайте количество точек
            double[][] parsedData = new double[2][countPoints];
            Map<String, String> firstVariableMap = variables.get(0);
            String firstValue = firstVariableMap.entrySet().iterator().next().getValue();
            double x = x0;

            for (int i = 0; i < countPoints; i++) {
                // Вычисление точек x в диапазоне x0-x1
                double y = 0;
                int var_num = 0;
                // Перебор всех переменных и подсчет функции для текущего x
                for (Map<String, String> variableMap : variables) {
                    for (Map.Entry<String, String> entry : variableMap.entrySet()) {
                        String value = entry.getValue();
                        y += Math.pow(x, var_num) * Double.parseDouble(value); // Расчет функции y для текущего x и переменной
                        var_num++;
                    }
                }

                parsedData[0][i] = x;
                parsedData[1][i] = y;
                x = x0 + (x1 - x0) * ((double) i / (countPoints));
            }
            return parsedData;
        }
        return null;
    }

    private static List<double[][]> parseF2A(String data) {
        List<double[][]> result = new ArrayList<>();

        String[] lines = data.split("\n");
        double[][] tempArray = null;
        List<Double> pressures = new ArrayList<>();
        List<Double> viscosities = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            String[] elements = line.trim().split("\\s+");

            if (elements.length == 1) {
                if (tempArray != null) {
                    tempArray[0] = pressures.stream().mapToDouble(Double::doubleValue).toArray();
                    tempArray[1] = viscosities.stream().mapToDouble(Double::doubleValue).toArray();
                    result.add(tempArray);
                }

                tempArray = new double[2][];
                pressures.clear();
                viscosities.clear();
            } else if (elements.length == 2) {
                pressures.add(Double.parseDouble(elements[0]));
                viscosities.add(Double.parseDouble(elements[1]));
            }
        }

        // Добавляем последние данные, если они остались
        if (tempArray != null && !pressures.isEmpty() && !viscosities.isEmpty()) {
            tempArray[0] = pressures.stream().mapToDouble(Double::doubleValue).toArray();
            tempArray[1] = viscosities.stream().mapToDouble(Double::doubleValue).toArray();
            result.add(tempArray);
        }

        return result;
    }
}
