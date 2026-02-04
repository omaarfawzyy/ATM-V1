package repositories.json;

import entities.Technician;
import repositories.TechnicianRepository;
import utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class JsonTechnicianRepository implements TechnicianRepository {

    private static final String FILE_PATH = "Data/technicians.json";

    @Override
    public List<Technician> findAll() {
        String json = JsonUtil.readFile(FILE_PATH);
        List<Technician> technicians = new ArrayList<>();

        String[] blocks = json.split("\\{");
        for (String block : blocks) {
            if (block.contains("username")) {
                String username = extract(block, "username");
                String password = extract(block, "password");
                technicians.add(new Technician(username, password));
            }
        }
        return technicians;
    }

    @Override
    public Technician findByUsername(String username) {
        return findAll()
                .stream()
                .filter(t -> t.getUsername().equals(username))
                .findFirst()
                .orElse(null);
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
}
