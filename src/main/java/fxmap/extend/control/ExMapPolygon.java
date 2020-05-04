package fxmap.extend.control;

import fxmapcontrol.IMapNode;
import fxmapcontrol.MapBase;
import fxmapcontrol.MapNodeHelper;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

import java.util.List;

/**
 * @see fxmapcontrol.MapPolygon
 */
public class ExMapPolygon extends Polygon implements IMapNode {

    private final ExMapPolyData exMapPolyData;
    private MapNodeHelper mapNodeHelper = new MapNodeHelper(e -> updatePoints());

    public ExMapPolygon(ExMapPolyData exMapPolyData) {
        this.exMapPolyData = exMapPolyData;

        getStyleClass().add("map-polygon");
        setFill(null);
        setStrokeLineJoin(StrokeLineJoin.ROUND);
        exMapPolyData.setUpdatePointRunner(this::updatePoints);
        exMapPolyData.runUpdatePoints();
    }

    public void setExLocation(ExPolyLocation exLocation) {
        exMapPolyData.setExLocation(exLocation);
    }

    public ExMapPolyData getExMapPolyData() {
        return this.exMapPolyData;
    }

    private void updatePoints() {
        List<Double> locationUpdatePointData = exMapPolyData.getUpdatedPoints(getMap());
        if (locationUpdatePointData.isEmpty()) {
            getPoints().setAll(0d, 0d); // clear() or empty collection is ignored
        } else {
            getPoints().setAll(locationUpdatePointData);
        }
    }

    @Override
    public final MapBase getMap() {
        return mapNodeHelper.getMap();
    }

    @Override
    public void setMap(MapBase map) {
        mapNodeHelper.setMap(map);
        exMapPolyData.runUpdatePoints();
    }
}
