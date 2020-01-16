import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.*;

public class RouteCalculatorTest extends TestCase {


    ArrayList<Station> route;
    RouteCalculator calculator;
    StationIndex stationIndex;
    Line line1;
    Line line2;
    Line line3;

    @Override
    protected void setUp() throws Exception {
        route = new ArrayList<>();
        stationIndex = new StationIndex();

        line1 = new Line(1, "red");
        line2 = new Line(2, "blue");
        line3 = new Line(3, "green");

        route.add(new Station("Лесная", line1));
        route.add(new Station("Выборгская", line1));
        route.add(new Station("Пионерская", line2));
        route.add(new Station("Удельная", line2));
        route.add(new Station("Беговая", line3));
        route.add(new Station("Рыбацкое", line3));


        line1.addStation(new Station("Лесная", line1));
        line1.addStation(new Station("Выборгская", line1));
        line1.addStation(new Station("Площадь Восстания", line1));
        line1.addStation(new Station("Технологический институт", line1));
        line1.addStation(new Station("Академическая", line1));

        line2.addStation(new Station("Пионерская", line2));
        line2.addStation(new Station("Удельная", line2));
        line2.addStation(new Station("Технологический институт", line2));
        line2.addStation(new Station("Купчино", line2));

        line3.addStation(new Station("Беговая", line3));
        line3.addStation(new Station("Маяковская", line3));
        line3.addStation(new Station("Рыбацкое", line3));

        stationIndex.addStation(new Station("Лесная", line1));
        stationIndex.addStation(new Station("Выборгская", line1));
        stationIndex.addStation(new Station("Площадь Восстания", line1));
        stationIndex.addStation(new Station("Технологический институт", line1));
        stationIndex.addStation(new Station("Академическая", line1));

        stationIndex.addStation(new Station("Пионерская", line2));
        stationIndex.addStation(new Station("Удельная", line2));
        stationIndex.addStation(new Station("Технологический институт", line2));
        stationIndex.addStation(new Station("Купчино", line2));

        stationIndex.addStation(new Station("Беговая", line3));
        stationIndex.addStation(new Station("Маяковская", line3));
        stationIndex.addStation(new Station("Рыбацкое", line3));

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        List<Station> connectionStation = new ArrayList<>();

        connectionStation.add(new Station("Технологический институт", line1));
        connectionStation.add(new Station("Технологический институт", line2));
        stationIndex.addConnection(connectionStation);

        connectionStation.clear();

        connectionStation.add(new Station("Площадь Восстания", line1));
        connectionStation.add(new Station("Маяковская", line3));

        stationIndex.addConnection(connectionStation);

    }

    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 14.5;
        assertEquals(expected, actual);
    }


    public void testGetShortestRoute() {
        calculator = new RouteCalculator(stationIndex);
        List<Station> actual = calculator.getShortestRoute(new Station("Лесная", line1), new Station("Выборгская", line1));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("Лесная", line1));
        expected.add(new Station("Выборгская", line1));
        assertEquals(expected, actual);
    }

    public void testGetShortestRoute2() {
        calculator = new RouteCalculator(stationIndex);
        List<Station> actual = calculator.getShortestRoute(new Station("Лесная", line1), new Station("Пионерская", line2));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("Лесная", line1));
        expected.add(new Station("Выборгская", line1));
        expected.add(new Station("Площадь Восстания", line1));
        expected.add(new Station("Технологический институт", line1));
        expected.add(new Station("Технологический институт", line2));
        expected.add(new Station("Удельная", line2));
        expected.add(new Station("Пионерская", line2));
        assertEquals(expected, actual);
    }

    public void testGetShortestRoute3() {
        calculator = new RouteCalculator(stationIndex);
        List<Station> actual = calculator.getShortestRoute(new Station("Пионерская", line2), new Station("Беговая", line3));
        List<Station> expected = new ArrayList<>();
        expected.add(new Station("Пионерская", line2));
        expected.add(new Station("Удельная", line2));
        expected.add(new Station("Технологический институт", line2));
        expected.add(new Station("Технологический институт", line1));
        expected.add(new Station("Площадь Восстания", line1));
        expected.add(new Station("Маяковская", line3));
        expected.add(new Station("Беговая", line3));
        assertEquals(expected, actual);
    }


    @Override
    protected void tearDown() throws Exception {

    }
}
