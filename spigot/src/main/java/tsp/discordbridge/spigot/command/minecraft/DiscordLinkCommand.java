package tsp.discordbridge.spigot.command.minecraft;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tsp.discordbridge.core.manager.LinkageManager;
import tsp.discordbridge.spigot.DiscordBridge;
import tsp.discordbridge.spigot.util.Utils;

import java.util.Optional;
import java.util.Random;

public class DiscordLinkCommand extends MinecraftCommand {

    public DiscordLinkCommand() {
        super(DiscordBridge.getInstance(), "discordlink");
    }

    @Override
    public void handleConsole(CommandSender sender, String[] args) {
        Utils.sendMessage(sender, "&cOnly for in-game players!");
    }

    @Override
    public void handle(CommandSender sender, String[] args) {
        Utils.sendMessage(sender, "&cYou are already linked to a discord account!");
    }

    @Override
    public void handleUnlinked(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;

        LinkageManager manager = DiscordBridge.getInstance().getLinkageManager();
        Optional<String> code = manager.getCode(player.getUniqueId());
        if (!code.isPresent()) {
            code = Optional.of(generateCode());
            manager.set(player.getUniqueId(), code.get());
        }

        TextComponent message = new TextComponent();
        message.setText(Utils.colorize("&7Your link code is: &b" + code.get() + " &7(Click to copy)"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, code.get()));
        player.spigot().sendMessage(message);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static final char[] pool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final Random random = new Random();

    /**
     * Generates a code from the pool above.
     *
     * @return code generated
     */
    private String generateCode() {
        char[] chars = new char[16];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = pool[random.nextInt(pool.length)];
        }

        return new String(chars);
    }

}
