package com.flash.astroniatimezone;

import com.flash.astroniatimezone.backend.AstroniaBackend;
import com.flash.astroniatimezone.backend.BackendType;
import com.flash.astroniatimezone.backend.backends.MongoBackend;
import com.flash.astroniatimezone.backend.backends.RedisBackend;
import com.flash.astroniatimezone.backend.creds.MongoCredentials;
import com.flash.astroniatimezone.backend.creds.RedisCredentials;
import com.flash.astroniatimezone.command.CommandManager;
import com.flash.astroniatimezone.game.player.AstroniaProfile;
import com.flash.astroniatimezone.listeners.ListenerManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter static Main instance;

    @Getter private AstroniaBackend backend;

    @Getter private CommandManager commandManager;
    @Getter private ListenerManager listenerManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        BackendType type = BackendType.getOrDefault(getConfig().getString("backend.driver"));
        switch (type) {
            case REDIS:
                backend = new RedisBackend(
                        new RedisCredentials(
                                getConfig().getString("backend.redis.host"),
                getConfig().getInt("backend.redis.port"),
                        getConfig().getString("backend.redis.pass")
                        )
                );
                break;
            case MONGO:
                backend = new MongoBackend(
                        new MongoCredentials(
                                getConfig().getString("backend.mongo.host"),
                                getConfig().getInt("backend.mongo.port"),
                                getConfig().getString("backend.mongo.auth.username"),
                                getConfig().getString("backend.mongo.auth.password"),
                                getConfig().getString("backend.mongo.database"),
                                getConfig().getString("backend.mongo.auth.authDb")
                        )
                );
                break;
        }

        if(!backend.isLoaded()) {
            getLogger().severe("Unable to connect to backend. Shutting down.");
            Bukkit.getServer().shutdown();
        }

        commandManager = new CommandManager();
        commandManager.registerCommands();

        listenerManager = new ListenerManager();
        listenerManager.registerListeners();

        AstroniaProfile.instate();
    }

    @Override
    public void onDisable() {
        if(backend != null && backend.isLoaded()) {
            AstroniaProfile.getProfiles().values().forEach(backend::saveProfileSync);

            backend.close();
        }
    }
}
