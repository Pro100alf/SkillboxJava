package sp;

import java.util.ArrayList;
import java.util.List;

public class SiteParserResult {
    private List<String> imgUrls;
    private List<String> imagesNotThisHost;

    public SiteParserResult(){
        imgUrls = new ArrayList<>();
        imagesNotThisHost = new ArrayList<>();
    }

    public List<String> getImgUrls(){
        return imgUrls;
    }

    public List<String> getImagesNotThisHost(){
        return imagesNotThisHost;
    }

    public void addImgUrl(String url){
        imgUrls.add(url);
    }

    public void addImgNotThisHost(String url){
        imagesNotThisHost.add(url);
    }
}
