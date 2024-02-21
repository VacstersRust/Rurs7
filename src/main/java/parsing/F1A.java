package parsing;

public class F1A {
    public static String[][] main(String data) {
        String[] lines = data.split("\\n");
        int numberOfPoints = lines.length;
        String[][] parsedData = new String[3][numberOfPoints];

        for (int i = 0; i < numberOfPoints; i++) {
            String[] values = lines[i].split("\\s+");

            if (values.length == 2) {
                try {
                parsedData[1][i] = values[0]; // x
                parsedData[2][i] = values[1]; // y
                } catch (NumberFormatException e) {
                e.printStackTrace();}
            }
        }
        parsedData[0][0] = " f1a";
        parsedData[0][1] = "Икс";
        parsedData[0][2] = "Игрик";
        return parsedData;
        }
}