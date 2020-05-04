package fxmap.extend.control;

import fxmapcontrol.Location;
import fxmapcontrol.MapBase;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class ExMapPolyData {

    private final ListProperty<ExPolyLocation> exLocations = new SimpleListProperty<>(this, "locations", FXCollections.observableArrayList());
    private final ObjectProperty<ExPolyLocation> exLocation = new SimpleObjectProperty<>(this, "location");
    private final SimpleBooleanProperty allowPolyLocationUpdate = new SimpleBooleanProperty(this, "allowPolyLocationUpdate", true);

    private Runnable updatePointRunner = () -> { };

    public ExMapPolyData() {
        exLocations.addListener((ListChangeListener.Change<? extends ExPolyLocation> c) -> {
            runUpdatePoints();
        });
        exLocation.addListener((observable, oldValue, newValue) -> {
            runUpdatePoints();
        });
        allowPolyLocationUpdate.addListener((observable, oldValue, newValue) -> {
            if (!oldValue && newValue) {
                runUpdatePoints();
            }
        });
    }

    public final ListProperty<ExPolyLocation> exLocationsProperty() {
        return exLocations;
    }

    public final ObservableList<ExPolyLocation> getExLocations() {
        return exLocations.get();
    }

    public final void setExLocations(ObservableList<ExPolyLocation> locations) {
        exLocations.set(locations);
    }

    public final ObjectProperty<ExPolyLocation> exLocationProperty() {
        return exLocation;
    }

    public final ExPolyLocation getExLocation() {
        return exLocation.get();
    }

    public final void setExLocation(ExPolyLocation location) {
        runUpdatePoints();
        exLocation.set(location);
    }

    public final BooleanProperty allowPolyLocationUpdateProperty() {
        return allowPolyLocationUpdate;
    }

    public void setAllowPolyLocationUpdate(boolean allowPolyLocationUpdate) {
        this.allowPolyLocationUpdate.set(allowPolyLocationUpdate);
    }

    public boolean isAllowPolyLocationUpdate() {
        return this.allowPolyLocationUpdate.get();
    }

    /**
     * Sets the update runnable and executes immediately.
     *
     * @param updatePointRunner new runner
     */
    public void setUpdatePointRunner(Runnable updatePointRunner) {
        if(updatePointRunner == null) {
            updatePointRunner = () -> {};
        }
        this.updatePointRunner = updatePointRunner;
        runUpdatePoints();
    }

    public void onExPolyLocationUpdate() {
        runUpdatePoints();
    }

    public void runUpdatePoints() {
        if (!isAllowPolyLocationUpdate()) {
            return;
        }
        updatePointRunner.run();
    }

    public List<Double> getUpdatedPoints(MapBase map) {
        if(map == null || CollectionUtils.isEmpty(exLocations) ) {
            return Collections.emptyList();
        }
        double longitudeOffset = getLongitudeOffset(map, getExLocation());

        ArrayList<Double> points = new ArrayList<>(exLocations.size() * 2);
        exLocations.stream()
                   .map(exLocation -> new Location(exLocation.getLatitude(), exLocation.getLongitude() + longitudeOffset))
                   .map(map::locationToView)
                   .filter(this::isValidPoint2D)
                   .forEach(point2D -> {
                               points.add(point2D.getX());
                               points.add(point2D.getY());
                           });

        if(points.size() != exLocations.size() * 2) {
            return Collections.emptyList();
        }

        return points;
    }

    private boolean isValidPoint2D(Point2D point2D) {
        return !Double.isInfinite(point2D.getX()) && !Double.isInfinite(point2D.getY());
    }

    private double getLongitudeOffset(MapBase map, ExPolyLocation mapExLocation) {
        double longitudeOffset = 0d;
        if (mapExLocation != null && map.getProjection()
                                        .isNormalCylindrical()) {
            Point2D viewportPosition = map.locationToView(mapExLocation.toLocation());

            double x = viewportPosition.getX();
            double y = viewportPosition.getY();
            if (x < 0d || x > map.getWidth()
                    || y < 0d || y > map.getHeight()) {

                double nearestLongitude = Location.nearestLongitude(mapExLocation.getLongitude(), map.getCenter()
                                                                                                  .getLongitude());
                longitudeOffset = nearestLongitude - mapExLocation.getLongitude();
            }
        }
        return longitudeOffset;
    }
}
