package repositories.json;

import entities.ATM;
import repositories.ATMRepository;
import utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class JsonATMRepository implements ATMRepository {

    private static final String FILE_PATH = "Data/atm.json";

    @Override
    public ATM loadATM() {
        String json = JsonUtil.readFile(FILE_PATH);

        Map<Integer, Integer> cash = new HashMap<>();
        cash.put(10, parseIntSafe(extract(json, "\"10\"")));
        cash.put(20, parseIntSafe(extract(json, "\"20\"")));
        cash.put(50, parseIntSafe(extract(json, "\"50\"")));
        cash.put(100, parseIntSafe(extract(json, "\"100\"")));

        int paper = parseIntSafe(extract(json, "\"paper\""));
        int ink = parseIntSafe(extract(json, "\"ink\""));
        String firmware = extract(json, "\"firmwareVersion\"");

        return new ATM(cash, paper, ink, firmware);
    }

    @Override
    public void saveATM(ATM atm) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"cash\": {\n");
        json.append("    \"10\": ").append(atm.getCash().get(10)).append(",\n");
        json.append("    \"20\": ").append(atm.getCash().get(20)).append(",\n");
        json.append("    \"50\": ").append(atm.getCash().get(50)).append(",\n");
        json.append("    \"100\": ").append(atm.getCash().get(100)).append("\n");
        json.append("  },\n");
        json.append("  \"paper\": ").append(atm.getPaper()).append(",\n");
        json.append("  \"ink\": ").append(atm.getInk()).append(",\n");
        json.append("  \"firmwareVersion\": \"").append(atm.getFirmwareVersion()).append("\"\n");
        json.append("}");

        JsonUtil.writeFile(FILE_PATH, json.toString());
    }

    // ---------------- SAFE JSON EXTRACTION ----------------
    private String extract(String json, String key) {
        int keyIndex = json.indexOf(key);
        if (keyIndex == -1) return "";

        int colonIndex = json.indexOf(":", keyIndex);
        int endIndex = json.indexOf(",", colonIndex);

        if (endIndex == -1) {
            endIndex = json.indexOf("\n", colonIndex);
        }

        String value = json.substring(colonIndex + 1, endIndex).trim();

        if (value.startsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }

    // ---------------- SAFE INTEGER PARSING ----------------
    private int parseIntSafe(String value) {
        value = value.replaceAll("[^0-9]", "");
        return value.isEmpty() ? 0 : Integer.parseInt(value);
    }
}
