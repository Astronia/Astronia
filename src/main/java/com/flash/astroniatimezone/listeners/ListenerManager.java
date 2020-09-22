package com.flash.astroniatimezone.listeners;

import com.flash.astroniatimezone.Main;
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
    }

    public void registerListener(Listener listener) {
        pluginManager.registerEvents(listener, Main.getInstance());
    }
}