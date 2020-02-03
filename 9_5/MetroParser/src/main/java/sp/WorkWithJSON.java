package sp;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WorkWithJSON {
    public static void CreateJSONFile(String path, SiteParserResult siteParserResult) {
        // stations parser result to JSON
        JSONObject JSONMain = new JSONObject();
        List<Station> allStations = siteParserResult.getAllStations();
        allStations.sort(Comparator.comparing(Station::getLine));
        String prevLine = allStations.get(0).getLine();
        JSONObject JSONStationsList = new JSONObject();
        JSONArray[] list = new JSONArray[siteParserResult.getAllLines().size() + 1];
        int index = 0;
        list[index] = new JSONArray();
        for (Station station : allStations) {
            if (station.getLine().contains(prevLine)) {
                list[index].add(station.getName());
                prevLine = station.getLine();
            } else {
                JSONStationsList.put(prevLine, list[index]);
                index += 1;
                list[index] = new JSONArray();
                list[index].add(station.getName());
                prevLine = station.getLine();
            }
        }
        JSONStationsList.put(prevLine, list[index]);
        JSONMain.put("stations", JSONStationsList);


        //connections parser result to JSON
        List<Connection> allConnections = siteParserResult.getAllConnections();
        JSONArray[] JSONConnectionsArray = new JSONArray[allConnections.size()];
        JSONArray JSONConnections = new JSONArray();
        index = 0;
        for (Connection connection : allConnections){
            JSONConnectionsArray[index] = new JSONArray();
            for (Station station : connection.getConnection()){
                JSONObject JSONConnectionsDetails = new JSONObject();
                JSONConnectionsDetails.put("line", station.getLine());
                JSONConnectionsDetails.put("station", station.getName());
                JSONConnectionsArray[index].add(JSONConnectionsDetails);
            }
            index += 1;
        }
        for(int i = 0; i < JSONConnectionsArray.length; i++){
            JSONConnections.add(JSONConnectionsArray[i]);
        }
        JSONMain.put("connections", JSONConnections);

        //lines parser result to JSON
        List<Line> allLines = siteParserResult.getAllLines();
        allLines.sort(Comparator.comparing(Line::getLine));
        JSONArray JSONLinesList = new JSONArray();
        for (Line line : allLines){
            JSONObject JSONLinesDetails = new JSONObject();
            JSONLinesDetails.put("number", line.getLine());
            JSONLinesDetails.put("name", line.getName());
            JSONLinesList.add(JSONLinesDetails);
        }
        JSONMain.put("lines", JSONLinesList);

        try (FileWriter file = new FileWriter(path)) {
            file.write(toPrettyFormat(JSONMain.toJSONString()));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printLineStationsNumb(SiteParserResult siteParserResult){
        List<Line> allLines = siteParserResult.getAllLines();
        allLines.forEach(line ->
                        System.out.println("Line: " + line.getName() + " stations number: " + siteParserResult.getLineStations(line).size())
                );
    }

    public static SiteParserResult GetJSONFileInfo(String path){
        SiteParserResult siteParserResult = new SiteParserResult();

        try {
           JSONParser parser = new JSONParser();
           JSONObject jsonData = (JSONObject) parser.parse(getJsonFile(path));

           JSONArray linesArray = (JSONArray) jsonData.get("lines");
           siteParserResult.addAllLines(parseLines(linesArray));

           JSONObject stationsObject = (JSONObject) jsonData.get("stations");
           siteParserResult.addAllStations(parseStations(stationsObject));
       }
         catch(Exception ex) {
            ex.printStackTrace();
        }
       return siteParserResult;
    }

    private static List<Station> parseStations(JSONObject stationsObject)
    {
        List<Station> allStations = new ArrayList<>();
        stationsObject.keySet().forEach(lineNumberObject ->
        {
            String lineNumber = (String) lineNumberObject;
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumberObject);
            stationsArray.forEach(stationObject ->
            {
                allStations.add(new Station(lineNumber, (String) stationObject));
            });
        });
        return allStations;
    }

    private static List<Line> parseLines(JSONArray linesArray)
    {
        List<Line> allLines = new ArrayList<>();
        linesArray.forEach(lineObject -> {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    ((String) lineJsonObject.get("number")),
                    (String) lineJsonObject.get("name")
            );
            allLines.add(line);
        });
        return  allLines;
    }

    private static String getJsonFile(String path)
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
}
