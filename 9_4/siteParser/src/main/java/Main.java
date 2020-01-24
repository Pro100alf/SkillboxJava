import sp.FileDownloader;
import sp.SiteParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String siteUrl = "https://lenta.ru/";
        String dirPath = "src/main/img/";
        List<String> imgUrls = SiteParser.imagesSiteParser(siteUrl);
        for (String url: imgUrls){
            FileDownloader.imageFileDownload(dirPath, url);
        }
    }
}
