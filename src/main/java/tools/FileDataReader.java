package tools;

import org.jfree.data.xy.XYSeriesCollection;
import parsing.DataType;
import parsing.ParsingAlgorithmFactory;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileDataReader {

        private final ParsingAlgorithmFactory parsingAlgorithmFactory;

        public FileDataReader() {
            this.parsingAlgorithmFactory = new ParsingAlgorithmFactory();
        }

        public List<XYSeriesCollection> readFileData(String filePath, DataType fileExtension) {
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

    public List<XYSeriesCollection> parseData(String data, DataType fileExtension) {
        if (fileExtension != null) {
            return parsingAlgorithmFactory.getAlgorithm(fileExtension).parse(data);
        }
        return null;
    }
}
