import java.io.File;

public class Main {

    private static final double BYTES_IN_KBYTES = 1024.0;
    private static final double BYTES_IN_MBYTES = 1024.0 * 1024.0;
    private static final double BYTES_IN_GBYTES = 1024.0 * 1024.0 * 1024.0;

    public static void main(String[] args) {
        try{
            String path = "/users/alf/IdeaProjects/bankAccount";
            File folder = new File(path);
            if (folder.isDirectory()) {
                filesFromFiles(folder);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void filesFromFiles(File folder){
        File[] folderEnt = folder.listFiles();
        for (File entry: folderEnt){
            if (entry.isDirectory()){
                filesFromFiles(entry);
                continue;
            }
            System.out.println(entry.getAbsolutePath());
            double fileLength = entry.length();
            if (fileLength > BYTES_IN_GBYTES - 24 * BYTES_IN_MBYTES){
                System.out.println("Length: " + String.format("%.2f",fileLength / BYTES_IN_GBYTES) + " GB");
            }
            else if (fileLength > BYTES_IN_MBYTES - 24 * BYTES_IN_KBYTES){
                System.out.println("Length: " + String.format("%.2f",fileLength / BYTES_IN_MBYTES) + " MB");
            }
            else if (fileLength > BYTES_IN_KBYTES - 24){
                System.out.println("Length: " + String.format("%.2f",fileLength / BYTES_IN_KBYTES) + " KB");
            }
            else {
                System.out.println("Length: " + String.format("%.2f",fileLength) + " B");
            }
            System.out.println();
        }
    }
}
