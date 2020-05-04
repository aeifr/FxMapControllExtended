//package fxmap.extend.scratch;
//
//import fxmapcontrol.IMapNode;
//import fxmapcontrol.Location;
//import fxmapcontrol.MapBase;
//import fxmapcontrol.MapNodeHelper;
//import javafx.beans.property.ListProperty;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleListProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Point2D;
//import javafx.scene.shape.Polyline;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapSplitPolyline extends Polyline implements IMapNode {
//
//    private final ListProperty<SplitLine> locationsProperty = new SimpleListProperty<>(this, "locations", FXCollections.observableArrayList());
//    private final ObjectProperty<SplitLine> locationProperty = new SimpleObjectProperty<>(this, "location");
//    private final MapNodeHelper mapNodeHelper = new MapNodeHelper(e -> updatePoints());
//
//    public static List<Double> updatePoints(MapBase map, SplitLine splitLine, ObservableList<SplitLine> splitLines) {
//        ArrayList<Double> points = null;
//
//        if (map != null && splitLines != null && !splitLines.isEmpty()) {
//            double longitudeOffset = 0d;
//
//            if (splitLine != null && map.getProjection().isNormalCylindrical()) {
//                Point2D viewportPosition = map.locationToView(splitLine);
//
//                if (viewportPosition.getX() < 0d || viewportPosition.getX() > map.getWidth()
//                        || viewportPosition.getY() < 0d || viewportPosition.getY() > map.getHeight()) {
//
//                    double nearestLongitude = Location.nearestLongitude(splitLine.getLongitude(), map.getCenter().getLongitude());
//                    longitudeOffset = nearestLongitude - splitLine.getLongitude();
//                }
//            }
//
//            points = new ArrayList<>(splitLines.size() * 2);
//
//            for (Location loc : splitLines) {
//                Point2D p = map.locationToView(
//                        new Location(loc.getLatitude(), loc.getLongitude() + longitudeOffset));
//
//                if (Double.isInfinite(p.getX()) || Double.isInfinite(p.getY())) {
//                    points = null;
//                    break;
//                }
//
//                points.add(p.getX());
//                points.add(p.getY());
//            }
//        }
//
//        return points;
//    }
//}
