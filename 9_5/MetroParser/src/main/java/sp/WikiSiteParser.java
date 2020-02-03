package sp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class WikiSiteParser {
    public static SiteParserResult WikiSiteParserParse(String url){
        SiteParserResult siteParserResult = new SiteParserResult();
        try {
            URL curUrl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(curUrl.openStream()));
            StringBuilder buffer = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null){
                buffer.append(inputLine);
            }
            Elements allTables = Jsoup.parse(buffer.toString()).select("tbody");
            getLinesStations(allTables.get(3), siteParserResult);
            getLinesStations(allTables.get(4), siteParserResult);
            getLinesStations(allTables.get(5), siteParserResult);
            getConnections(allTables.get(3), siteParserResult);
            getConnections(allTables.get(4), siteParserResult);
            getConnections(allTables.get(5), siteParserResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return siteParserResult;
    }

    private static SiteParserResult getLinesStations(Element mainTable, SiteParserResult siteParserResult){
        Elements allTr = mainTable.select("tr");
        //parser all stations and all lines
        for (Element tr: allTr){
            if (tr.select(".shadow").size() == 0) {
                Elements td = tr.select("td");
                if (td.size() > 0) {
                    Elements lineNumberSpans = td.get(0).select("span");
                    Elements stationNameHref = td.get(1).select("a[href]");
                    if (lineNumberSpans.size() == 3) {
                        String line = lineNumberSpans.get(0).text();
                        String lineName = lineNumberSpans.get(1).attr("title");
                        String stationName = stationNameHref.get(0).text();
                        siteParserResult.addLine(new Line(line, lineName));
                        siteParserResult.addStation(new Station(line, stationName));
                    }
                    else if (lineNumberSpans.size() == 5){
                        String line = lineNumberSpans.get(0).text();
                        String name = lineNumberSpans.get(1).attr("title");
                        String stationName = stationNameHref.get(0).text();
                        siteParserResult.addLine(new Line(line, name));
                        siteParserResult.addStation(new Station(line, stationName));
                        line = lineNumberSpans.get(2).text();
                        name = lineNumberSpans.get(3).attr("title");
                        siteParserResult.addLine(new Line(line, name));
                        siteParserResult.addStation(new Station(line, stationName));
                    }
                    //System.out.println(td.get(2));
                }
            }
        }
        return siteParserResult;
    }

    private static SiteParserResult getConnections(Element mainTable, SiteParserResult siteParserResult){
        //parser for all connections
        Elements allTr = mainTable.select("tr");
        for (Element tr: allTr){
            if (tr.select(".shadow").size() == 0) {
                Elements td = tr.select("td");
                if (td.size() > 0) {
                    Elements lineNumberSpans = td.get(0).select("span");
                    Elements stationNameHref = td.get(1).select("a[href]");
                    Elements connectionsSpans = td.get(3).select("span");
                    if (lineNumberSpans.size() == 3) {
                        String line = lineNumberSpans.get(0).text();
                        String stationName = stationNameHref.get(0).text();
                        siteParserResult.addConnection(wikiConnection(new Station(line, stationName), connectionsSpans, siteParserResult));
                    }
                    else if (lineNumberSpans.size() == 5){
                        String line = lineNumberSpans.get(0).text();
                        String stationName = stationNameHref.get(0).text();
                        siteParserResult.addConnection(wikiConnection(new Station(line, stationName), connectionsSpans, siteParserResult));
                        line = lineNumberSpans.get(2).text();
                        siteParserResult.addConnection(wikiConnection(new Station(line, stationName), connectionsSpans, siteParserResult));
                    }
                }
            }
        }
        return siteParserResult;
    }

    private static Connection wikiConnection(Station station, Elements connectionsSpan, SiteParserResult siteParserResult){
        final String CROSS_PLATFORM_TRANSFER = "Кросс-платформенная пересадка на станцию";
        final String PLATFORM_TRANSFER = "Переход на станцию";
        Connection connection = new Connection();
        if (connectionsSpan.size() > 0){
            connection.addStation(station);
            for (int i = 0; i < connectionsSpan.size(); i += 2){
                String line = connectionsSpan.get(i).text();
                String stationString = connectionsSpan.get(i+1).attr("title");
                if (stationString.contains(PLATFORM_TRANSFER)) {
                    String[] stationStringSplit = stationString.substring(PLATFORM_TRANSFER.length()).trim().split(" ");
                    String[] lineName = siteParserResult.getLineName(line).split(" ");
                    String stationName = String.join(" ",
                            Arrays.copyOfRange(stationStringSplit, 0, stationStringSplit.length - lineName.length));
                    connection.addStation(new Station(line, stationName));
                }
                else if (stationString.contains(CROSS_PLATFORM_TRANSFER)){
                    String[] stationStringSplit = stationString.substring(CROSS_PLATFORM_TRANSFER.length()).trim().split(" ");
                    String[] lineName = siteParserResult.getLineName(line).split(" ");
                    String stationName = String.join(" ",
                            Arrays.copyOfRange(stationStringSplit, 0, stationStringSplit.length - lineName.length));
                    connection.addStation(new Station(line, stationName));
                }
            }
        }
        return connection;
    }
}
