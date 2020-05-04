package fxmap.extend.sampleApplication;

import fxmap.extend.control.ExMapPolyData;
import fxmap.extend.control.ExMapPolyline;
import fxmap.extend.control.ExPolyLocation;
import fxmap.extend.control.ExMapPolygon;
import fxmap.extend.scratch.SplitPolyLine;
import fxmapcontrol.BingMapsTileLayer;
import fxmapcontrol.EquirectangularProjection;
import fxmapcontrol.GnomonicProjection;
import fxmapcontrol.ImageFileCache;
import fxmapcontrol.Location;
import fxmapcontrol.MapBase;
import fxmapcontrol.MapGraticule;
import fxmapcontrol.MapNode;
import fxmapcontrol.MapProjection;
import fxmapcontrol.StereographicProjection;
import fxmapcontrol.MapTileLayer;
import fxmapcontrol.OrthographicProjection;
import fxmapcontrol.TileImageLoader;
import fxmapcontrol.WebMercatorProjection;
import fxmapcontrol.WmsImageLayer;
import fxmapcontrol.WmtsTileLayer;
import fxmapcontrol.WorldMercatorProjection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class FXMLController implements Initializable {

    @FXML
    private CheckBox shiftPolygonsCheckBox;
    @FXML
    private MapBase map;
    @FXML
    private MapGraticule mapGraticule;
    @FXML
    private Slider zoomSlider;
    @FXML
    private Slider headingSlider;
    @FXML
    private CheckBox seamarksCheckBox;
    @FXML
    private ComboBox mapLayerComboBox;
    @FXML
    private ComboBox projectionComboBox;

    @FXML
    private void handlePressed(MouseEvent event) {
        if (event.getTarget() == map && event.getClickCount() == 2) {
            map.setTargetCenter(map.viewToLocation(new Point2D(event.getX(), event.getY())));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        GeoToolsProjection wgs84zone32N = new GeoToolsProjection();
//        wgs84zone32N.setWKT("PROJCS[\"WGS 84 / UTM zone 32N\","
//                + "GEOGCS[\"WGS 84\","
//                + "DATUM[\"WGS_1984\","
//                + "SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],"
//                + "AUTHORITY[\"EPSG\",\"6326\"]],"
//                + "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
//                + "UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],"
//                + "AUTHORITY[\"EPSG\",\"4326\"]],"
//                + "PROJECTION[\"Transverse_Mercator\"],"
//                + "PARAMETER[\"latitude_of_origin\",0],"
//                + "PARAMETER[\"central_meridian\",9],"
//                + "PARAMETER[\"scale_factor\",0.9996],"
//                + "PARAMETER[\"false_easting\",500000],"
//                + "PARAMETER[\"false_northing\",0],"
//                + "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
//                + "AXIS[\"Easting\",EAST],"
//                + "AXIS[\"Northing\",NORTH],"
//                + "AUTHORITY[\"EPSG\",\"32632\"]]");
//
//        GeoToolsProjection etrs89zone32N = new GeoToolsProjection();
//        etrs89zone32N.setWKT("PROJCS[\"ETRS89 / UTM zone 32N\","
//                + "GEOGCS[\"ETRS89\","
//                + "DATUM[\"European_Terrestrial_Reference_System_1989\","
//                + "SPHEROID[\"GRS 1980\",6378137,298.257222101,AUTHORITY[\"EPSG\",\"7019\"]],"
//                + "TOWGS84[0,0,0,0,0,0,0],"
//                + "AUTHORITY[\"EPSG\",\"6258\"]],"
//                + "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
//                + "UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],"
//                + "AUTHORITY[\"EPSG\",\"4258\"]],"
//                + "PROJECTION[\"Transverse_Mercator\"],"
//                + "PARAMETER[\"latitude_of_origin\",0],"
//                + "PARAMETER[\"central_meridian\",9],"
//                + "PARAMETER[\"scale_factor\",0.9996],"
//                + "PARAMETER[\"false_easting\",500000],"
//                + "PARAMETER[\"false_northing\",0],"
//                + "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
//                + "AXIS[\"Easting\",EAST],"
//                + "AXIS[\"Northing\",NORTH],"
//                + "AUTHORITY[\"EPSG\",\"25832\"]]");
//
//        GeoToolsProjection upsNorth = new GeoToolsProjection();
//        upsNorth.setWKT("PROJCS[\"WGS 84 / UPS North (N,E)\","
//                + "GEOGCS[\"WGS 84\","
//                + "DATUM[\"WGS_1984\","
//                + "SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],"
//                + "AUTHORITY[\"EPSG\",\"6326\"]],"
//                + "PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],"
//                + "UNIT[\"degree\",0.0174532925199433,AUTHORITY[\"EPSG\",\"9122\"]],"
//                + "AUTHORITY[\"EPSG\",\"4326\"]],"
//                + "PROJECTION[\"Polar_Stereographic\"],"
//                + "PARAMETER[\"latitude_of_origin\",90],"
//                + "PARAMETER[\"central_meridian\",0],"
//                + "PARAMETER[\"scale_factor\",0.994],"
//                + "PARAMETER[\"false_easting\",2000000],"
//                + "PARAMETER[\"false_northing\",2000000],"
//                + "UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],"
//                + "AUTHORITY[\"EPSG\",\"32661\"]]");


        TileImageLoader.setCache(new ImageFileCache());

        map.targetZoomLevelProperty().bindBidirectional(zoomSlider.valueProperty());
        map.targetHeadingProperty().bindBidirectional(headingSlider.valueProperty());

        HashMap<String, Node> mapLayers = new HashMap<>();
        mapLayers.put("OpenStreetMap", MapTileLayer.getOpenStreetMapLayer());
        mapLayers.put("OpenStreetMap DE", new MapTileLayer("OpenStreetMap German", "https://tile.openstreetmap.de/tiles/osmde/{z}/{x}/{y}.png"));
        mapLayers.put("Seamarks", new MapTileLayer("Seamarks", "http://tiles.openseamap.org/seamark/{z}/{x}/{y}.png", 9, 18));
        mapLayers.put("Stamen Terrain", new MapTileLayer("Stamen Terrain", "http://tile.stamen.com/terrain/{z}/{x}/{y}.png"));
        mapLayers.put("OpenStreetMap WMS", new WmsImageLayer("http://ows.terrestris.de/osm/service"));
        mapLayers.put("ChartServer WMS", new ChartServerLayer("https://wms.sevencs.com:9090"));
        mapLayers.put("TopPlusOpen WMS", new WmsImageLayer("https://sgx.geodatenzentrum.de/wms_topplus_open"));
        mapLayers.put("TopPlusOpen WMTS", new WmtsTileLayer("TopPlusOpen", "https://sgx.geodatenzentrum.de/wmts_topplus_open/1.0.0/WMTSCapabilities.xml"));
        //mapLayers.put("Bing Maps Aerial", new BingMapsTileLayer(BingMapsTileLayer.MapMode.Aerial));

        mapLayerComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Node mapLayer = mapLayers.get((String) newValue);

                if (mapLayers.containsValue(map.getChildren().get(0))) {
                    map.getChildren().set(0, mapLayer);
                } else {
                    map.getChildren().add(0, mapLayer);
                }

                if (mapLayer instanceof BingMapsTileLayer
                        && ((BingMapsTileLayer) mapLayer).getMapMode() != BingMapsTileLayer.MapMode.Road) {
                    map.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    mapGraticule.setStroke(Color.WHITE);
                    mapGraticule.setTextFill(Color.WHITE);
                } else {
                    map.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    mapGraticule.setStroke(Color.BLACK);
                    mapGraticule.setTextFill(Color.BLACK);
                }
            }
        });
        mapLayerComboBox.getSelectionModel().select(0);

        seamarksCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Node mapLayer = mapLayers.get("Seamarks");
            if (newValue) {
                map.getChildren().add(1, mapLayer);
            } else {
                map.getChildren().remove(mapLayer);
                map.getChildren()
                   .stream()
                   .filter(ExMapPolygon.class::isInstance)
                   .map(ExMapPolygon.class::cast)
                   .findFirst()
                   .ifPresent(exMapPolygon -> exMapPolygon.setExLocation(null));
            }
        });

        MapProjection[] projections = new MapProjection[]{
                new WebMercatorProjection(),
                new WorldMercatorProjection(),
                new EquirectangularProjection(),
                new OrthographicProjection(),
                new GnomonicProjection(),
                new StereographicProjection(),
//            wgs84zone32N,
//            etrs89zone32N,
//            upsNorth
        };

        projectionComboBox.getSelectionModel().select(0);
        projectionComboBox.getSelectionModel()
                          .selectedIndexProperty()
                          .addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int index = newValue.intValue();
                map.setProjection(projections[index]);
            }
        });

        for (double lon = -180d; lon < 180d; lon += 15) {
            ExMapPolyData exMapPolyData = new ExMapPolyData();
            ExMapPolygon polygon = new ExMapPolygon(exMapPolyData);
            ExPolyLocation location1 = new ExPolyLocation(0d, lon - 5d, exMapPolyData);
            exMapPolyData.getExLocations().add(location1);
            ExPolyLocation location2 = new ExPolyLocation(-5d, lon, exMapPolyData);
            exMapPolyData.getExLocations().add(location2);
            ExPolyLocation location3 = new ExPolyLocation(0d, lon + 5d, exMapPolyData);
            exMapPolyData.getExLocations().add(location3);
            ExPolyLocation location4 = new ExPolyLocation(5d, lon, exMapPolyData);
            exMapPolyData.getExLocations().add(location4);
            exMapPolyData.setExLocation(new ExPolyLocation(0d, lon, exMapPolyData));
            polygon.setStroke(Color.RED);

            SplitPolyLine splitPolyLine = new SplitPolyLine(location2, location4, map);

            map.getChildren().add(polygon);
            map.getChildren().add(splitPolyLine);
        }

        for (double lon = -180d; lon < 180d; lon += 15) {
            String text = lon < 0d ? String.format(" W %.0f ", -lon) : String.format(" E %.0f ", lon);

            Label label = new Label(text);
            label.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            label.setTranslateY(-20);

            MapNode pushpin = new MapNode();
            pushpin.setLocation(new Location(0, lon));
            pushpin.getChildren().add(label);

            map.getChildren().add(pushpin);
        }

        shiftPolygonsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            double shiftValue = newValue ? -10 : +10;
            List<ExMapPolyData> collect =
                    map.getChildren()
                       .stream()
                       .filter(ExMapPolygon.class::isInstance)
                       .map(ExMapPolygon.class::cast)
                       .map(ExMapPolygon::getExMapPolyData)
                       .collect(Collectors.toList());
            collect.forEach(exMapPolygon -> exMapPolygon.setAllowPolyLocationUpdate(false));
            collect.stream()
                         .map(ExMapPolyData::getExLocations)
                         .flatMap(Collection::stream)
                         .forEach(exLocation -> exLocation.setLatitude(exLocation.getLatitude() + shiftValue));
            collect.forEach(exMapPolygon -> exMapPolygon.setAllowPolyLocationUpdate(true));
        });
    }

    private class ChartServerLayer extends WmsImageLayer {

        public ChartServerLayer(String serviceUrl) {
            super(serviceUrl);
            setLayers("ENC");
        }

        @Override
        protected String getImageUrl() {
            String url = super.getImageUrl()
                    .replace("&CRS=AUTO2:97001,", "&CRS=AUTO2:7CS01,")
                    .replace("&CRS=AUTO2:97002,", "&CRS=AUTO2:7CS02,");

            //System.out.println(url);
            return url;
        }
    }
}
