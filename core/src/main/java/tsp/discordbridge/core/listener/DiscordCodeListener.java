package tsp.discordbridge.core.listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import tsp.discordbridge.core.BridgeCore;
import tsp.discordbridge.core.manager.DataManager;
import tsp.discordbridge.core.manager.LinkageManager;
import tsp.discordbridge.core.player.BridgedPlayer;

import java.util.Optional;
import java.util.UUID;

public class DiscordCodeListener extends ListenerAdapter {

    private final LinkageManager linkageManager;
    private final DataManager dataManager;

    public DiscordCodeListener(BridgeCore core) {
        this.linkageManager = core.getLinkageManager();
        this.dataManager = core.getDataManager();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.isFromType(ChannelType.PRIVATE)) {
            return; // Only private messages
        }
        PrivateChannel channel = event.getPrivateChannel();
        String message = event.getMessage().getContentRaw();
        Optional<UUID> gameId = linkageManager.getGameId(message);
        gameId.ifPresent(uuid -> {
            if (dataManager.isLinked(uuid)) {
                channel.sendMessage("Your account is already linked!").queue();
                return;
            }

            dataManager.add(new BridgedPlayer(uuid, channel.getUser().getIdLong()));
            channel.sendMessage("Your account has been linked!").queue();
        });
    }

}
