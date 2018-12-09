package me.mark.nf;

import me.mark.nf.csvreader.CsvReader;
import me.mark.nf.csvreader.CsvWriter;
import me.mark.nf.kml.Coordinate;
import me.mark.nf.kml.GLCoord;
import me.mark.nf.kml.JsonFile;
import me.mark.nf.kml.KmlFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        try {
            CsvReader products = new CsvReader("/Users/markvolkov/Desktop/NYPDNF/data.csv");

            ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
            ArrayList<GLCoord> glCoords = new ArrayList<GLCoord>();

            //Mapping all offenses to their classifications
            HashMap<String, String> offenseToClass = new HashMap<>();
            int i = 20000;
            while (products.readRecord()) {
                String crime = products.get("Offense");
                String classification = products.get("Offense Classification");
                String location = products.get("Location 1");
                location = location.substring(1, location.length()-1);
                String latitude = location.split(",")[0];
                String longitude = location.split(",")[1];
                if (i > 0) {
                    coordinates.add(new Coordinate(crime, Float.valueOf(longitude), Float.valueOf(latitude)));
                    glCoords.add(new GLCoord(crime, Float.valueOf(longitude), Float.valueOf(latitude)));
                    i--;
                }
                if (offenseToClass.containsKey(crime)) continue;
                offenseToClass.put(crime, classification);
            }

            products.close();
            for (Map.Entry<String, String> entry : offenseToClass.entrySet()) {
                System.out.println("Offense: " + entry.getKey() + " Classification: " + entry.getValue());
            }

            insertIntoOffenseMapTable(offenseToClass);

            JsonFile file = new JsonFile("gldata", glCoords);
            file.writeJson();
            KmlFile kmlFile = new KmlFile("geocrime", coordinates);
            kmlFile.writeToFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void insertIntoOffenseMapTable(HashMap<String, String> mapping) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:data.db");
            for (Map.Entry<String, String> entry : mapping.entrySet()) {
                String sql = "INSERT INTO OffenseMap(offense, classification) VALUES(?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, entry.getKey());
                statement.setString(2, entry.getValue());
                statement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Done writing to mapping table");
    }

    public static void printHeaders(CsvReader reader) throws IOException {
        reader.readHeaders();
        for (String header : reader.getHeaders()) {
            System.out.println(header);
        }
    }


}
