package com.flash.astroniatimezone.utils;

import com.flash.astroniatimezone.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValues {

    /*=============================*/
    // Server
    public static final String SERVER_NAME, SERVER_PRIMARY, SERVER_SECONDARY, SERVER_WEBSITE, SERVER_STORE;
    /*=============================*/

    static {
        FileConfiguration config = Main.getInstance().getConfig();

        SERVER_NAME = config.getString("server.name");
        SERVER_PRIMARY = config.getString("server.color.primary");
        SERVER_SECONDARY = config.getString("server.color.secondary");
        SERVER_WEBSITE = config.getString("server.website").replace("%servername%", SERVER_NAME.toLowerCase());
        SERVER_STORE = config.getString("server.store").replace("%servername%", SERVER_NAME.toLowerCase());
    }
}
