package fxmap.extend.control;

import fxmapcontrol.Location;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;

/**
 * @see fxmapcontrol.Location
 */
public class ExPolyLocation {
    private SimpleDoubleProperty latitude = new SimpleDoubleProperty(this, "latitude", 0.);
    private SimpleDoubleProperty longitude = new SimpleDoubleProperty(this, "longitude", 0.);

    private final ExMapPolyData parent;

    /**
     * Public constructor.
     *
     * @param latitude
     * @param longitude
     * @param parent
     */
    public ExPolyLocation(double latitude, double longitude, ExMapPolyData parent) {
        this.latitude.set(latitude);
        this.longitude.set(longitude);
        this.parent = parent;
        this.latitude.addListener((observable, oldValue, newValue) -> parent.onExPolyLocationUpdate());
        this.longitude.addListener((observable, oldValue, newValue) -> parent.onExPolyLocationUpdate());
    }

    /**
     * Transformed this location into a decoupled {@link Location}.
     *
     * @return a location
     */
    public Location toLocation() {
        return new Location(latitude.get(), longitude.get());
    }

    public static double greatCircleAzimuth(ExPolyLocation exPolyLocation, ExPolyLocation location2) {
        return Location.greatCircleAzimuth(exPolyLocation.toLocation(), location2.toLocation());
    }

    public static ExPolyLocation greatCircleLocation(ExPolyLocation exPolyLocation, double azimuth, double distance) {
        Location location = Location.greatCircleLocation(exPolyLocation.toLocation(), azimuth, distance);
        return new ExPolyLocation(location.getLatitude(), location.getLongitude(), exPolyLocation.parent);
    }

    //==========================================================
    //
    //Private methods
    //
    //==========================================================



    //==========================================================
    //
    //Getter/Setter of properties
    //
    //==========================================================
    public SimpleDoubleProperty latitudeProperty() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public double getLatitude() {
        return latitude.get();
    }

    public SimpleDoubleProperty longitudeProperty() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    public double getLongitude() {
        return this.longitude.get();
    }

    //==========================================================
    //
    //Equals and HashCode
    //
    //==========================================================

    public final boolean equals(ExPolyLocation location) {
        if(this == location) {
            return true;
        }
        SimpleDoubleProperty latitudeProperty = location.latitudeProperty();
        SimpleDoubleProperty longitudeProperty = location.longitudeProperty();
        if(this.latitude == latitudeProperty && this.longitude == longitudeProperty) {
            return true;
        }
        return this.latitude.get() == latitudeProperty.get() && this.longitude.get() == longitudeProperty.get();
    }

    public final boolean equals(Object obj) {
        return obj instanceof ExPolyLocation && this.equals((ExPolyLocation) obj);
    }

    public int hashCode() {
        return Double.hashCode(this.latitude.get()) ^ Double.hashCode(this.longitude.get());
    }

    public static class UpdatePointContainer {
        private final int pointIndex;
        private final double value;

        public UpdatePointContainer(int pointIndex, double value) {
            this.pointIndex = pointIndex;
            this.value = value;
        }

        public int getPointIndex() {
            return pointIndex;
        }

        public double getValue() {
            return value;
        }
    }
}
