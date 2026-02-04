package repositories.json;

import entities.User;
import repositories.UserRepository;
import utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class JsonUserRepository implements UserRepository {

    private static final String FILE_PATH = "Data/users.json";

    @Override
    public List<User> findAll() {
        String json = JsonUtil.readFile(FILE_PATH);
        List<User> users = new ArrayList<>();

        String[] blocks = json.split("\\{");
        for (String block : blocks) {
            if (block.contains("cardNumber")) {
                String card = extract(block, "cardNumber");
                String pin = extract(block, "pin");

                String balanceStr = extract(block, "balance");
                double balance = parseDoubleSafe(balanceStr);

                users.add(new User(card, pin, balance));
            }
        }
        return users;
    }

    @Override
    public User findByCardNumber(String cardNumber) {
        return findAll()
                .stream()
                .filter(u -> u.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveAll(List<User> users) {
        StringBuilder json = new StringBuilder("{ \"users\": [\n");

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            json.append("  { ");
            json.append("\"cardNumber\": \"").append(u.getCardNumber()).append("\", ");
            json.append("\"pin\": \"").append(u.getPin()).append("\", ");
            json.append("\"balance\": ").append(u.getBalance());
            json.append(" }");

            if (i < users.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("]}");
        JsonUtil.writeFile(FILE_PATH, json.toString());
    }

    // ---------------- SAFE JSON EXTRACTION ----------------
    private String extract(String block, String key) {
        int keyIndex = block.indexOf("\"" + key + "\"");
        if (keyIndex == -1) return "";

        int colonIndex = block.indexOf(":", keyIndex);
        int endIndex = block.indexOf(",", colonIndex);

        if (endIndex == -1) {
            endIndex = block.indexOf("}", colonIndex);
        }

        String value = block.substring(colonIndex + 1, endIndex).trim();

        if (value.startsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }

    // ---------------- SAFE DOUBLE PARSING ----------------
    private double parseDoubleSafe(String value) {
        value = value.replaceAll("[^0-9.]", "");
        return value.isEmpty() ? 0.0 : Double.parseDouble(value);
    }
}
