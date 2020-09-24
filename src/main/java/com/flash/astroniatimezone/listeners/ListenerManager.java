package com.flash.astroniatimezone.listeners;

import com.flash.astroniatimezone.Main;
import com.flash.astroniatimezone.listeners.player.PlayerListener;
import com.flash.astroniatimezone.listeners.player.ScytherListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    @Getter
    private PluginManager pluginManager;

    public ListenerManager() {
        this.pluginManager = Bukkit.getServer().getPluginManager();
    }

    public void registerListeners() {
        registerListener(new PlayerListener());
        registerListener(new ScytherListener());
    }

    public void registerListener(Listener listener) {
        pluginManager.registerEvents(listener, Main.getInstance());
    }
}