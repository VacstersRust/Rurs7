package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDataReader {

    public double[][] readFile(String filePath) {
        List<double[]> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s+");

                if (values.length == 2) {
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);

                    dataList.add(new double[]{x, y});
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return dataList.toArray(new double[0][]);
    }

    public static double[][] parseData(String data) {
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


}

