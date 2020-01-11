import com.skillbox.airport.*;

import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        final long SECOND = 1000;
        final long HOUR = 3600 * SECOND;

        Date now = new Date();
        Date nowPlus2Hour = new Date(now.getTime() + 2 * HOUR + SECOND);

        Airport air = Airport.getInstance();

        List<Terminal> allTerminals = air.getTerminals();

        for (Terminal terminal : allTerminals) {
            terminal.getFlights().stream()
                    .filter(f -> f.getDate().after(now))
                    .filter(f -> f.getDate().before(nowPlus2Hour))
                    .forEach(f -> {
                        System.out.println(f.getDate());
                        System.out.println(f.getAircraft());
                    });
        }

    }
}
