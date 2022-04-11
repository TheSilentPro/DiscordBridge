package tsp.discordbridge.core.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LinkageManager {

    private final Map<UUID, String> codes = new HashMap<>(); // gameId, code

    public void set(UUID uuid, String code) {
        codes.put(uuid, code);
    }

    public Optional<String> getCode(UUID uuid) {
        return Optional.ofNullable(codes.get(uuid));
    }

    public Optional<UUID> getGameId(String code) {
        UUID uuid = null;
        for (Map.Entry<UUID, String> entry : codes.entrySet()) {
            if (code.equalsIgnoreCase(entry.getValue())) {
                uuid = entry.getKey();
                break;
            }
        }

        return Optional.ofNullable(uuid);
    }

    public Map<UUID, String> getCodes() {
        return Collections.unmodifiableMap(codes);
    }

}
