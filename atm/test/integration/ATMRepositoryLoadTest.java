package integration;

import entities.ATM;
import repositories.json.JsonATMRepository;

import java.nio.file.Files;
import java.nio.file.Path;

public class ATMRepositoryLoadTest {

    public static void main(String[] args) throws Exception {
        Path dataDir = Path.of("Data");
        Files.createDirectories(dataDir);

        Path atmPath = dataDir.resolve("atm.json");
        String original = Files.exists(atmPath) ? Files.readString(atmPath) : null;

        try {
            String atmJson = "{\n" +
                    "  \"cash\": {\n" +
                    "    \"10\": 2,\n" +
                    "    \"20\": 3,\n" +
                    "    \"50\": 0,\n" +
                    "    \"100\": 0\n" +
                    "  },\n" +
                    "  \"paper\": 4,\n" +
                    "  \"firmwareVersion\": \"1.0.0\"\n" +
                    "}";
            Files.writeString(atmPath, atmJson);

            JsonATMRepository repo = new JsonATMRepository();
            ATM atm = repo.loadATM();

            assertEquals(2, atm.getCash().get(10), "cash 10s");
            assertEquals(3, atm.getCash().get(20), "cash 20s");
            assertEquals(4, atm.getPaper(), "paper count");
            System.out.println("ATMRepositoryLoadTest: Test passed");
        } finally {
            if (original == null) {
                Files.deleteIfExists(atmPath);
            } else {
                Files.writeString(atmPath, original);
            }
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
}
