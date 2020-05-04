package fxmap.extend.elements;

import fxmap.extend.scratch.SplitPolyLine;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.Setter;

public class Corner {

    @Getter
    @Setter
    private Corner previousCorner;
    @Getter
    @Setter
    private Corner nextCorner;

    @Getter
    @Setter
    private SplitPolyLine previousLine;

    @Getter
    private SimpleDoubleProperty positionX = new SimpleDoubleProperty(this, "positionX", 0);
    @Getter
    private SimpleDoubleProperty positionY = new SimpleDoubleProperty(this, "positionY", 0);

    /**
     * Creates a new instance of {@code Point2D}.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public Corner(double x, double y) {
        positionX.set(x);
        positionY.set(y);
    }

    public final double getX() {
        return positionX.get();
    }

    public final double getY() {
        return positionY.get();
    }

    public void bindToCorners(Corner previousCorner, Corner nextCorner) {
        this.previousCorner = previousCorner;
        this.nextCorner = nextCorner;
    }

    public Point2D toPoint() {
        return new Point2D(getX(), getY());
    }
}
