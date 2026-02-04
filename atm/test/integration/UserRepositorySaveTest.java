package integration;

import entities.User;
import repositories.json.JsonUserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class UserRepositorySaveTest {

    public static void main(String[] args) throws Exception {
        Path dataDir = Path.of("Data");
        Files.createDirectories(dataDir);

        Path usersPath = dataDir.resolve("users.json");
        String original = Files.exists(usersPath) ? Files.readString(usersPath) : null;

        try {
            JsonUserRepository repo = new JsonUserRepository();
            List<User> users = List.of(new User("9", "1111", 20.0));
            repo.saveAll(users);

            User reloaded = repo.findByCardNumber("9");
            assertTrue(reloaded != null, "user saved and reloaded");
            assertEquals(20.0, reloaded.getBalance(), "balance persisted");
            System.out.println("UserRepositorySaveTest: Test passed");
        } finally {
            if (original == null) {
                Files.deleteIfExists(usersPath);
            } else {
                Files.writeString(usersPath, original);
            }
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertEquals(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.0001) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
}
