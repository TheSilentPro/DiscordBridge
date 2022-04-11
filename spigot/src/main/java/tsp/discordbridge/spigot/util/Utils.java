package tsp.discordbridge.spigot.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

    public static void sendMessage(CommandSender receiver, String message) {
        receiver.sendMessage(colorize(message));
    }

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
