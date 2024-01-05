package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

    public class FileDataReader {

        public static String[][] readFileData(String filePath, String fileExtension) {
        try {
            StringBuilder content = new StringBuilder();
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();

            return parseData(content.toString(), fileExtension);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[][] parseData(String data, String fileExtension) {
        if (fileExtension != null) {
            if (fileExtension.equals("dat") || fileExtension.equals("f1a")) {
                return parsing.F1A.main(data);
            } else if (fileExtension.equals("f0a")) {
                return parsing.F0A.main(data);
            } else if (fileExtension.equals("f2a")) {
                return parsing.F2A.main(data);
            }
        }
        return null;
    }
}
