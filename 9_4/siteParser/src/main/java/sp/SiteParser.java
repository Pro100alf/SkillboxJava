package sp;

import org.jsoup.Jsoup;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SiteParser {
    public static List<String> imagesSiteParser(String url) {
        List<String> imgUrls = new ArrayList<>();
        try {
            String html = Jsoup.connect(url).get().html();
            imgUrls = Jsoup.parse(html).select("img")
                    .stream().map(p -> p.absUrl("src"))
                    .collect(Collectors.toList());
        }
        catch (Exception ex){
            ex.getStackTrace();
            System.out.println(ex);
        }
        return imgUrls;
    }
}
