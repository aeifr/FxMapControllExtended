package fxmap.extend.scratch;

import fxmap.extend.control.ExPolyLocation;
import fxmap.extend.util.data.DragDelta;
import fxmapcontrol.IMapNode;
import fxmapcontrol.Location;
import fxmapcontrol.MapBase;
import fxmapcontrol.MapNodeHelper;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SplitPolyLine extends Pane implements IMapNode {
    private final Circle startCircle;
    private final Circle centerCircle;
    private final Circle endCircle;

    private DragDelta dragDelta;

    private ExPolyLocation start;
    private ExPolyLocation end;
    private MapNodeHelper mapNodeHelper = new MapNodeHelper(e ->  updatePoints());

    private void updatePoints() {
        Point2D startPoint = mapNodeHelper.getMap().locationToView(start.toLocation());
        Point2D endPoint = mapNodeHelper.getMap().locationToView(end.toLocation());
        startCircle.setCenterX(startPoint.getX());
        startCircle.setCenterY(startPoint.getY());

        endCircle.setCenterX(endPoint.getX());
        endCircle.setCenterY(endPoint.getY());
    }

    private SimpleBooleanProperty showDraggableSpots = new SimpleBooleanProperty(this, "showDraggableSpots", false);
    private SimpleBooleanProperty showSplitCenter = new SimpleBooleanProperty(this, "showSplitCenter", false);

    private SimpleDoubleProperty circleRadius = new SimpleDoubleProperty(this, "circleRadius", 5.0);
    private SimpleObjectProperty<Color> circleEndpointColor = new SimpleObjectProperty<>(this, "circleEndpointColor", Color.BLACK);
    private SimpleObjectProperty<Color> circleCenterColor = new SimpleObjectProperty<>(this, "circleEndpointColor", Color.BLACK);

    public SplitPolyLine(ExPolyLocation startLocation, ExPolyLocation endLocation, MapBase map) {
        this.start = startLocation;
        this.end = endLocation;
        this.startCircle = createEndpoint();
        this.startCircle.centerXProperty().bindBidirectional(startLocation.latitudeProperty());
        this.endCircle = createEndpoint();
        this.centerCircle = createEndpoint(Bindings.add(startCircle.centerXProperty(), endCircle.centerXProperty())
                                                   .divide(2.0),
                                           Bindings.add(startCircle.centerYProperty(), endCircle.centerYProperty())
                                                   .divide(2.0));
        postCircleInit();

        this.getChildren()
            .addAll(startCircle, centerCircle, endCircle);
    }

    private void postCircleInit() {
        attachEventListeners();

        startCircle.fillProperty().bind(circleEndpointColor);
        centerCircle.fillProperty().bind(circleCenterColor);
        endCircle.fillProperty().bind(circleEndpointColor);
    }

    private void attachEventListeners() {
        centerCircle.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressEvent -> {
            dragDelta = new DragDelta(mousePressEvent.getX(), mousePressEvent.getY());
        });

        centerCircle.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEvent -> {
            if(mouseClickEvent.getClickCount() == 2) {
                System.out.println("hi, split me here");
            }
        });

        centerCircle.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragEvent -> {
            double differenceX = dragDelta.updateDifferenceX(mouseDragEvent.getX());
            double differenceY = dragDelta.updateDifferenceY(mouseDragEvent.getY());

            updateOffset(startCircle, differenceX, differenceY);
            updateOffset(endCircle, differenceX, differenceY);
        });

        addDragListenerToCircle(startCircle);
        addDragListenerToCircle(endCircle);
    }

    private void addDragListenerToCircle(Circle circlePoint) {
        circlePoint.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragEvent -> {
            circlePoint.setCenterX(mouseDragEvent.getX());
            circlePoint.setCenterY(mouseDragEvent.getY());
        });
    }

    private void updateOffset(Circle circlePoint, double differenceX, double differenceY) {
        circlePoint.setCenterX(circlePoint.getCenterX() - differenceX);
        circlePoint.setCenterY(circlePoint.getCenterY() - differenceY);
    }

    private <T extends DoubleProperty> Circle createEndpoint() {
        Circle circle = new Circle();
        circle.radiusProperty().bind(circleRadius);
        return circle;
    }

    private <T extends NumberBinding> Circle createEndpoint(T centerX, T centerY) {
        Circle circle = new Circle();
        circle.centerXProperty().bind(centerX);
        circle.centerYProperty().bind(centerY);
        circle.radiusProperty().bind(circleRadius);
        return circle;
    }

    @Override
    public MapBase getMap() {
        return null;
    }

    @Override
    public void setMap(MapBase mapBase) {

    }
}
