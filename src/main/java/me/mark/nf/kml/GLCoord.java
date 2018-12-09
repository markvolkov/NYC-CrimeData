package me.mark.nf.kml;

public class GLCoord {

    private class Geometry {
        private final String type = "Point";
        private float[] coordinates;
        public Geometry(float latitude, float longitude) {
            this.coordinates = new float[2];
            coordinates[0] = latitude;
            coordinates[1] = longitude;
        }
    }

    private class Properties {
        private String title;
        public Properties(String title) {
            this.title = title;
        }

    }

    private final String type = "Feature";
    private Geometry geometry;
    private Properties properties;

    public GLCoord(String title, float latitude, float longitude) {
        this.geometry = new Geometry(latitude, longitude);
        this.properties = new Properties(title);
    }



}
