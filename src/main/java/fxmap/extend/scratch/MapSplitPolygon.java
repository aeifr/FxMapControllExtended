//package fxmap.extend.scratch;
//
//import fxmapcontrol.*;
//import javafx.beans.property.ListProperty;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleListProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.collections.FXCollections;
//import javafx.scene.shape.Polygon;
//
//import java.util.List;
//
//public class MapSplitPolygon extends Polygon implements IMapNode {
//
//    private final ListProperty<SplitLine> splitLinesProperty = new SimpleListProperty<>(this, "splitLines", FXCollections.observableArrayList());
//    private final ObjectProperty<SplitLine> splitLineProperty = new SimpleObjectProperty<>(this, "splitLine");
//    private final MapNodeHelper mapNodeHelper = new MapNodeHelper(e -> updatePoints());
//
//    public void addAfter(SplitLine splitPolygon) {
//        MapSplitPolyline.updatePoints(mapBase, Location location, ObservableList<Location> locations);
//    }
//
//    @Override
//    public MapBase getMap() {
//        return mapBase;
//    }
//
//    @Override
//    public void setMap(MapBase mapBase) {
//        this.mapBase = mapBase;
//    }
//
//    private void updatePoints() {
//        List<Double> points = MapSplitPolyline.updatePoints(getMap(), getLocation(), getLocations());
//
//        if (points != null) {
//            getPoints().setAll(points);
//        } else {
//            getPoints().setAll(new Double[]{0d, 0d}); // clear() or empty collection is ignored
//        }
//    }
//
//}
