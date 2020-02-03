package sp;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
public class SiteParserResult {
    private List<Station> allStations = new ArrayList<>();
    private List<Line>  allLines = new ArrayList<>();
    private List<Connection> allConnections = new ArrayList<>();

    public void addStation(Station station){
        allStations.add(station);
    }

    public void addLine(Line line) {
        if (!allLines.contains(line)){
            allLines.add(line);
        }
    }

    public List<Line> getAllLines(){
        return allLines;
    }

    public List<Station> getAllStations(){
        return allStations;
    }

    public void addConnection(Connection newConnection){
        boolean isNotInConnection = true;
        if (newConnection.getConnection().size() > 1){
            for (Connection connection: allConnections){
                for (Station stationsInNewConnection: newConnection.getConnection()) {
                    if(connection.getConnection().contains(stationsInNewConnection)){
                        isNotInConnection = false;
                        break;
                    }
                }
            }
            if (isNotInConnection){
                allConnections.add(newConnection);
            }
        }
    }

    public List<Connection> getAllConnections(){
        return allConnections;
    }

    public String getLineName(String lineNumb){
        String lineName = "";
        for (Line line: allLines){
            if (line.getLine().contains(lineNumb)){
                return line.getName();
            }
        }
        return lineName;
    }

    public List<Station> getLineStations(Line line){
        List<Station> lineStations = new ArrayList<>();
        for (Station station: allStations){
            if (station.getLine().contains(line.getLine())){
                lineStations.add(station);
            }
        }
        return lineStations;
    }

    public void addAllLines(List<Line> lines){
        for (Line line: lines){
            addLine(line);
        }
    }

    public void addAllStations(List<Station> stations){
        for (Station station: stations){
            addStation(station);
        }
    }
}
