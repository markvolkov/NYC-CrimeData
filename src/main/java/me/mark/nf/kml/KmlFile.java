package me.mark.nf.kml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KmlFile {

    private String fileName;
    private List<Coordinate> coordinates;

    public KmlFile(String fileName) {
        this.fileName = fileName;
        this.coordinates = new ArrayList<Coordinate>();
    }

    public KmlFile(String fileName, ArrayList<Coordinate> coordinates) {
        this.fileName = fileName;
        this.coordinates = coordinates;
    }

    public void writeToFile() {
        File file = new File(fileName + ".kml");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
            writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
            writer.write("  <Document id=\"root_doc\">\n");
            for (Coordinate coordinate : this.coordinates) {
                System.out.println(coordinate.toString());
                writer.write("      <Placemark><name>" + coordinate.getCrime() + "</name><Point><coordinates>" + coordinate.getLongitude() + "," + coordinate.getLatitude() + "</coordinates></Point></Placemark>\n");
            }
            writer.write("</Document>\n");
            writer.write("</kml>");
            writer.flush();
            writer.close();
            System.out.println("Done making kml file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
