package dto;

public class Simple2DPoint {

    public Simple2DPoint(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    private Double x;
    private Double y;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
