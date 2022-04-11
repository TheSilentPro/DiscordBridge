package tsp.discordbridge.spigot;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.discordbridge.core.Bot;
import tsp.discordbridge.core.BridgeCore;
import tsp.discordbridge.core.Settings;
import tsp.discordbridge.core.listener.DiscordCodeListener;
import tsp.discordbridge.core.manager.DataManager;
import tsp.discordbridge.core.manager.LinkageManager;
import tsp.discordbridge.spigot.command.minecraft.DiscordLinkCommand;
import tsp.discordbridge.spigot.util.Logger;

import javax.security.auth.login.LoginException;
import java.io.File;

/**
 * Spigot implementation of DiscordBridge plugin.
 *
 * @author TheSilentPro (Silent)
 */
public class DiscordBridge extends JavaPlugin implements BridgeCore {

    private static DiscordBridge instance;
    private Logger logger;
    private Settings settings;
    private Bot bot;
    private DataManager dataManager;
    private LinkageManager linkageManager;

    @SuppressWarnings("ConstantConditions") // suppress: config getter may produce npe
    @Override
    public void onEnable() {
        instance = this;
        this.logger = new Logger();
        this.logger.info("Loading DiscordBridge - " + getDescription().getVersion());
        saveDefaultConfig();

        // Managers
        this.dataManager = new DataManager(new File(getDataFolder(), "data.json")).createAndLoad();
        this.linkageManager = new LinkageManager();

        // Configure settings
        this.settings = new Settings.Builder()
                .token(getConfig().getString("bot.token"))
                .status(OnlineStatus.fromKey(getConfig().getString("bot.status")))
                .activity(getConfig().getBoolean("bot.activity.enabled")
                        ? Activity.of(Activity.ActivityType.valueOf(getConfig().getString("bot.activity.type").toUpperCase()), getConfig().getString("bot.activity.name"), getConfig().getString("bot.activity.url"))
                        : null
                )
                .build();

        // Commands
        registerDefaultCommands();

        // Start bot
        this.bot = new Bot(this.settings);
        try {
            this.bot.init();
            this.bot.getJDA().addEventListener(new DiscordCodeListener(this));
        } catch (LoginException e) {
            this.logger.error("Could not login to the bot!");
            e.printStackTrace();
            this.setEnabled(false);
            return;
        }

        this.logger.info("Bot is running on account: " + this.bot.getJDA().getSelfUser().getAsTag());
        this.logger.info("Bot invite link: " + this.bot.getJDA().getInviteUrl(Permission.ADMINISTRATOR));
        this.logger.info("Done!");
    }

    @Override
    public void onDisable() {
        this.dataManager.save();
        this.bot.getJDA().shutdown();
    }

    private void registerDefaultCommands() {
        new DiscordLinkCommand();
    }

    public LinkageManager getLinkageManager() {
        return linkageManager;
    }

    public Logger getLog() {
        return logger;
    }

    public Settings getSettings() {
        return settings;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Bot getBot() {
        return bot;
    }

    public static DiscordBridge getInstance() {
        return instance;
    }

}