package tsp.discordbridge.spigot.util;

import org.bukkit.Bukkit;
import tsp.discordbridge.core.util.AbstractLogger;
import tsp.discordbridge.spigot.DiscordBridge;

public class Logger extends AbstractLogger {

    public Logger() {
        super("DiscordBridge", DiscordBridge.getInstance().getConfig().getBoolean("debug"));
    }

    @Override
    public void log(LogLevel level, String message) {
        Bukkit.getConsoleSender().sendMessage(Utils.colorize("&7[&9&l" + getName() + "&7] " + level.getColor() + "[" + level.name() + "]: " + message + "\u001B[0m")); // Reset color after message
    }

}
