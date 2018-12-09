package me.mark.nf.kml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonFile {

    private class Features {
        private List<GLCoord> coords;

        public Features(List<GLCoord> coords) {
            this.coords = coords;
        }
    }
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String fileName;
    private List<GLCoord> coordinates;
    private Features features;


    public JsonFile(String fileName, List<GLCoord> coordinates) {
        this.fileName = fileName;
        this.coordinates = coordinates;
        this.features = new Features(coordinates);
    }


    public void writeJson() {
        File file = new File(fileName + ".json");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(features).replaceAll("\"", ""));
            writer.flush();
            writer.close();
            System.out.println("Finished writing json data");
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

}
