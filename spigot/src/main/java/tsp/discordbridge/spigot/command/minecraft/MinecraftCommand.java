package tsp.discordbridge.spigot.command.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import tsp.discordbridge.core.player.BridgedPlayer;
import tsp.discordbridge.spigot.DiscordBridge;
import tsp.discordbridge.spigot.util.Utils;

import java.util.Optional;

public abstract class MinecraftCommand implements CommandExecutor {

    private final String name;
    private final String permission;

    public MinecraftCommand(JavaPlugin plugin, String name, String permission) {
        PluginCommand command = plugin.getCommand(name);
        if (command != null) {
            command.setExecutor(this);
        }
        this.name = name;
        this.permission = permission;
    }

    public MinecraftCommand(JavaPlugin plugin, String name) {
        this(plugin, name, "");
    }

    public void sendNoPermission(CommandSender sender) {
        Utils.sendMessage(sender, "&cYou do not have permission for this command!");
    }

    public abstract void handle(CommandSender sender, String[] args);

    public void handleUnlinked(CommandSender sender, String[] args) {
        Utils.sendMessage(sender, "&cYou must link your discord account first! &e/discordlink");
    }

    public void handleConsole(CommandSender sender, String[] args) {

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] args) {
        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sendNoPermission(sender);
            return true;
        }
        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender) {
            handleConsole(sender, args);
            return true;
        }

        if (!(sender instanceof Player)) {
            return true;
        }

        Optional<BridgedPlayer> bp = DiscordBridge.getInstance().getDataManager().getPlayer(((Player) sender).getUniqueId());
        if (!bp.isPresent()) {
            // not linked to discord
            handleUnlinked(sender, args);
            return true;
        }

        handle(sender, args);
        return true;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

}
