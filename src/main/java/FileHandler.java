import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileHandler {
    public static String DEFAULT_PATH;
    static {
        URL url = FileHandler.class.getResource("test.txt");
        Path basePath = null;
        try {
            basePath = Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        DEFAULT_PATH = basePath+"/../../../../../src/main/resources/test.txt";
    }

    public String getStringfromFile(String path) {
        System.out.println();
        Scanner scanner = null;
        StringBuilder strFromFile = new StringBuilder();
        try {
            scanner = new Scanner(Paths.get(path));
            while (scanner.hasNextLine()) {
                strFromFile.append(scanner.nextLine()+"\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return strFromFile.toString();
    }

    public void saveStringToFile(String str, String path) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path, false);
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
