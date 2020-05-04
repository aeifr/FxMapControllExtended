package fxmap.extend.util.data;

public class DragDelta {
    private double originX;
    private double originY;

    public DragDelta(double originX, double originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public double updateDifferenceX(double referenceX) {
        double diff = originX - referenceX;
        this.originX = referenceX;
        return diff;
    }

    public double updateDifferenceY(double referenceY) {
        double diff = originY - referenceY;
        this.originY = referenceY;
        return diff;
    }
}