package com.flash.astroniatimezone.utils;

import com.flash.astroniatimezone.Main;
import com.flash.astroniatimezone.api.chat.C;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public enum Locale {

    PLAYER_ONLY("primary.player-only", "&cYou must be a player to execute this command."),
    NO_PERMISSION("primary.no-permission", "&cNo permission."),
    COMMAND_NOT_FOUND("primary.not-found", "&fUnknown command."),
    PLAYER_NOT_FOUND("primary.player-not-found", "&c%name% is not currently online."),
    INVALID_NUMBER("primary.invalid-number", "&c%number% is not a valid number."),
    SUGAR_CANE_TOP_INTRODUCTION("leaderboard.sugarcane.introduction", "&cTop 10 Sugar Cane Leaderboard"),
    SUGAR_CANE_LEADERBOARD_MESSAGE("leaderboard.sugarcane.message", "&a%number%. &f%name% %value%");

    @Getter private static File file;
    @Getter private static YamlConfiguration config;

    @Getter private String path, def;

    Locale(String path, String def) {
        this.path = path;
        this.def = def;
    }

    public String getDefault() {
        return this.def;
    }

    @Override
    public String toString() {
        return C.color(config.getString(path, def)
                .replace("%primary%", ConfigValues.SERVER_PRIMARY)
                .replace("%secondary%", ConfigValues.SERVER_SECONDARY)
                .replace("%store%", ConfigValues.SERVER_STORE)
                .replace("%website%", ConfigValues.SERVER_WEBSITE));
    }

    public static void load(JavaPlugin plugin, boolean override) {
        Main.getInstance().getDataFolder().mkdirs();
        file = new File(Main.getInstance().getDataFolder(), "locale.yml");

        if (!file.exists()) {
            try {
                plugin.getLogger().info("locale.yml doesn't exist, creating..");

                if(file.createNewFile()) {
                    plugin.getLogger().info("Successfully created locale.yml.");
                } else {
                    plugin.getLogger().severe("Failed to create locale.yml.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.shutdown();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

        for (Locale item : values()) {
            if (override || config.getString(item.getPath()) == null)
                config.set(item.getPath(), item.getDefault());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
