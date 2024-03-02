package parsing;

public enum DataType {

    F0A("f0a"),
    F1A("f1a"),
    F2A("f2a");


    private final String dataType;

    DataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return dataType;
    }
}
