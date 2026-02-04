package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUtil {

    public static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path);
        }
    }

    public static void writeFile(String path, String content) {
        try {
            Files.writeString(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + path);
        }
    }
}
