import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) {
        try{
            String path = "/users/alf/IdeaProjects/bankAccount";
            String newPath = "/users/alf/Downloads/New";
            File folder = new File(path);
            File newFolder = new File(newPath);
            if (folder.isDirectory()) {
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                copyFiles(folder, path, newPath);
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void copyFiles(File folder, String oldPath, String newPath) throws IOException {
        File[] folderEnt = folder.listFiles();
        for (File entry: folderEnt){
            String tempNewPath = entry.getAbsolutePath().replace(oldPath, newPath);
            File tempNewFile = new File(tempNewPath);
            if (entry.isDirectory()){
                if (!tempNewFile.exists()) {
                    tempNewFile.mkdir();
                }
                copyFiles(entry, oldPath, newPath);
                continue;
            }
            if (!tempNewFile.exists()){
                tempNewFile.createNewFile();
            }
            FileChannel source = null;
            FileChannel destination = null;
            source = new FileInputStream(entry).getChannel();
            destination = new FileOutputStream(tempNewFile).getChannel();
            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }

        }
    }
}
