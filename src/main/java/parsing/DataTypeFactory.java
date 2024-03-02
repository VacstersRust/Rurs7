package parsing;

public class DataTypeFactory {
    public DataType getType(String dataType) {
        for (DataType type : DataType.values()) {
            if (dataType.equals(type.toString()))
                return type;
        }
        return null;
    }
}
