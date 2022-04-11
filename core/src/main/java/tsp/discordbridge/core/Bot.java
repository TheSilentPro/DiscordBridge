package tsp.discordbridge.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import tsp.discordbridge.core.util.Validate;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class Bot {

    private final Settings settings;
    private JDA jda;

    public Bot(@Nonnull Settings settings) {
        Validate.notNull(settings, "Settings can not be null!");

        this.settings = settings;
    }

    public void init() throws LoginException {
        this.jda = JDABuilder.createDefault(settings.getToken(),
                        GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES)
                .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
                .setStatus(settings.getStatus())
                .setActivity(settings.getActivity())
                .build();
    }

    @Nonnull
    public JDA getJDA() {
        Validate.validate(jda, "JDA is null!");
        return jda;
    }

    @Nonnull
    public Settings getSettings() {
        return settings;
    }

}
