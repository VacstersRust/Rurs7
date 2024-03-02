package parsing;

import java.util.Arrays;

public class ParsingAlgorithmFactory {

    private final ParsingAlgorithm[] parsingAlgorithms = {
            new F0aParsingAlgorithm(),
            new F1aParsingAlgorithm(),
            new F2aParsingAlgorithm()
    };

    public ParsingAlgorithm getAlgorithm(DataType dataType) {
        for (ParsingAlgorithm parsingAlgorithm : parsingAlgorithms) {
            if (parsingAlgorithm.canProcessThisType(dataType))
                return parsingAlgorithm;
        }
        return null;
    }
}
