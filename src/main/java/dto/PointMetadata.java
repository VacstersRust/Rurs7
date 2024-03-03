package dto;

import parsing.DataType;

import java.time.LocalDateTime;

public class PointMetadata {

    private DataType dataType;
    private LocalDateTime localDateTime;
    private String sample;
    private String technician;

    public PointMetadata(DataType dataType, LocalDateTime localDateTime, String sample, String technician) {
        this.dataType = dataType;
        this.localDateTime = localDateTime;
        this.sample = sample;
        this.technician = technician;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }
}
