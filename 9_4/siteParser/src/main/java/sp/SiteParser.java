package sp;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SiteParser {
    public static SiteParserResult imagesSiteParser(String url) {
        List<String> imgUrls = new ArrayList<>();
        SiteParserResult siteParserResult = new SiteParserResult();
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
        for (String curImgUrl: imgUrls){
            String splitUrl = url.split("/+")[1];
            if (curImgUrl.indexOf(splitUrl) == -1){
                siteParserResult.addImgNotThisHost(curImgUrl);
            }
            else{
                siteParserResult.addImgUrl(curImgUrl);
            }
        }
        return siteParserResult;
    }
}
