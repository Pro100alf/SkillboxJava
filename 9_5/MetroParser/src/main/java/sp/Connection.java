package sp;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@ToString
public class Connection {
    private List<Station> connection;

    public Connection(){
        connection = new ArrayList<>();
    }

    public void addStation(Station station){
        if (!connection.contains(station)){
            connection.add(station);
        }
    }

    public List<Station> getConnection(){
        return connection;
    }

}
