package tsp.discordbridge.core;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import tsp.discordbridge.core.util.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Settings {

    private @Nonnull String token;
    private @Nonnull OnlineStatus status;
    private @Nullable Activity activity;

    public Settings() {
        this.token = "";
        this.status = OnlineStatus.ONLINE;
        this.activity = null;
    }

    @Nonnull
    public OnlineStatus getStatus() {
        return status;
    }

    @Nullable
    public Activity getActivity() {
        return activity;
    }

    @Nonnull
    public String getToken() {
        return token;
    }

    public void setToken(@Nonnull String token) {
        Validate.notNull(token, "Token can not be null!");
        this.token = token;
    }

    public void setStatus(@Nonnull OnlineStatus status) {
        Validate.notNull(status, "Settings can not be null!");
        this.status = status;
    }

    public void setActivity(@Nullable Activity activity) {
        this.activity = activity;
    }

    public static class Builder {

        private String token;
        private OnlineStatus status;
        private Activity activity;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder status(OnlineStatus status) {
            this.status = status;
            return this;
        }

        public Builder activity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Settings build() {
            Settings settings = new Settings();
            settings.setToken(token);
            settings.setStatus(status);
            settings.setActivity(activity);
            return settings;
        }

    }

}
