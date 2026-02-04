package repositories.json;

import entities.Transaction;
import repositories.TransactionRepository;
import utils.JsonUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonTransactionRepository implements TransactionRepository {

    private static final String FILE_PATH = "Data/transactions.json";

    @Override
    public List<Transaction> findAll() {
        String json = JsonUtil.readFile(FILE_PATH);
        List<Transaction> transactions = new ArrayList<>();

        String[] blocks = json.split("\\{");
        for (String block : blocks) {
            if (block.contains("cardNumber")) {
                String cardNumber = extract(block, "cardNumber");
                String type = extract(block, "type");
                double amount = parseDoubleSafe(extract(block, "amount"));

                transactions.add(
                        new Transaction(
                                cardNumber,
                                type,
                                amount,
                                LocalDateTime.now()
                        )
                );
            }
        }
        return transactions;
    }

    @Override
    public void save(Transaction transaction) {
        List<Transaction> transactions = findAll();
        transactions.add(transaction);

        StringBuilder json = new StringBuilder("{ \"transactions\": [\n");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);

            json.append("  { ");
            json.append("\"cardNumber\": \"").append(t.getCardNumber()).append("\", ");
            json.append("\"type\": \"").append(t.getType()).append("\", ");
            json.append("\"amount\": ").append(t.getAmount());
            json.append(" }");

            if (i < transactions.size() - 1) {
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
