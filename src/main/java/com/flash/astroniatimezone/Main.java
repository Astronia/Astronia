package com.flash.astroniatimezone;

import com.flash.astroniatimezone.command.CommandManager;
import com.flash.astroniatimezone.listeners.ListenerManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter static Main instance;

    @Getter private CommandManager commandManager;
    @Getter private ListenerManager listenerManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        commandManager = new CommandManager();
        commandManager.registerCommands();

        listenerManager = new ListenerManager();
        listenerManager.registerListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
