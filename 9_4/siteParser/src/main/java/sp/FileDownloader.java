package sp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownloader {

    private static final double BYTES_IN_KBYTES = 1024.0;
    private static final double BYTES_IN_MBYTES = 1024.0 * 1024.0;
    private static final double BYTES_IN_GBYTES = 1024.0 * 1024.0 * 1024.0;

    public static void imageFileDownload(String path, String fileUrl){
        Logger logger = LogManager.getLogger();
        String[] splitPath = fileUrl.split("/");
        int fileNameLength = splitPath.length;
        String fileName = splitPath[fileNameLength - 1];
        long fileSizeInBytes = 0;
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(path+fileName)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileSizeInBytes += dataBuffer.length;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println(fileUrl + "\t:OK");
            logger.info(String.format("%s - %s", fileName, trueSizeFormat(fileSizeInBytes)));
            logger.info(String.format("%s - %d", fileName, fileSizeInBytes));
        } catch (IOException ex) {
            ex.getStackTrace();
            System.out.println(ex + fileUrl);
        }

    }

    private static String trueSizeFormat(long fileLength){
        String stringFileLength ;
        if (fileLength > BYTES_IN_GBYTES - 24 * BYTES_IN_MBYTES){
           stringFileLength = String.format("%.2f",fileLength / BYTES_IN_GBYTES) + " GB";
        }
        else if (fileLength > BYTES_IN_MBYTES - 24 * BYTES_IN_KBYTES){
           stringFileLength =  String.format("%.2f",fileLength / BYTES_IN_MBYTES) + " MB";
        }
        else if (fileLength > BYTES_IN_KBYTES - 24){
            stringFileLength = String.format("%.2f",fileLength / BYTES_IN_KBYTES) + " KB";
        }
        else {
            stringFileLength = String.format("%.2d",fileLength) + " B";
        }
        return stringFileLength;
    }
}
