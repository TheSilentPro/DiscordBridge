package tsp.discordbridge.core.player;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Represents a player that has a discord account linked to their game account.
 */
public class BridgedPlayer {

    @SerializedName("gameId")
    private final UUID uuid; // Game
    @SerializedName("discordId")
    private final long userId; // Discord

    public BridgedPlayer(UUID gameId, long userId) {
        this.uuid = gameId;
        this.userId = userId;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public long getUserId() {
        return userId;
    }

}
