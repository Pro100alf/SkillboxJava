package sp;


import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownloader {
    public static void imageFileDownload(String path, String fileUrl){
        String[] splitPath = fileUrl.split("/");
        int fileNameLength = splitPath.length;
        String fileName = splitPath[fileNameLength - 1];
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path+fileName)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println(fileUrl + "\t:OK");
        } catch (IOException ex) {
            ex.getStackTrace();
            System.out.println(ex + fileUrl);
        }

    }
}
