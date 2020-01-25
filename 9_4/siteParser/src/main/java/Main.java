import sp.FileDownloader;
import sp.SiteParser;
import sp.SiteParserResult;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String siteUrl = "https://lenta.ru/";
        String dirPath = "src/main/img/";

        SiteParserResult siteParserResult = SiteParser.imagesSiteParser(siteUrl);
        List<String> imgUrls = siteParserResult.getImgUrls();
        for (String url: imgUrls){
            FileDownloader.imageFileDownload(dirPath, url);
        }
    }
}
