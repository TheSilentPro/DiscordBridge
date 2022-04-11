package tsp.discordbridge.core.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import tsp.discordbridge.core.player.BridgedPlayer;
import tsp.discordbridge.core.util.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DataManager {

    public static final Type TYPE_TOKEN = new TypeToken<List<BridgedPlayer>>(){}.getType();
    private final Gson gson = new Gson();
    private final List<BridgedPlayer> data = new ArrayList<>();
    private final File file;

    public DataManager(File file) {
        Validate.notNull(file, "File can not be null!");
        this.file = file;
    }

    public void add(BridgedPlayer player) {
        data.add(player);
    }

    public Optional<BridgedPlayer> fromDiscordId(long id) {
        return data.stream().filter(player -> player.getUserId() == id).findFirst();
    }

    public Optional<BridgedPlayer> getPlayer(UUID gameId) {
        return data.stream().filter(player -> player.getUniqueId().equals(gameId)).findFirst();
    }

    public boolean isLinked(UUID gameId) {
        return getPlayer(gameId).isPresent();
    }

    public DataManager createAndLoad() {
        try {
            this.createIfAbsent();
            this.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public void load() throws FileNotFoundException {
        JsonArray main = JsonParser.parseReader(new FileReader(file)).getAsJsonArray();
        for (int i = 0; i < main.size(); i++) {
            JsonObject entry = main.get(i).getAsJsonObject();
            data.add(new BridgedPlayer(
                    UUID.fromString(entry.get("gameId").getAsString()),
                    entry.get("discordId").getAsLong()
            ));
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(toJson());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void createIfAbsent() throws IOException {
        if (!file.exists() && file.createNewFile()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]");
            }
        }
    }

    public String toJson() {
        return gson.toJson(data, TYPE_TOKEN);
    }

    public File getFile() {
        return file;
    }

}
