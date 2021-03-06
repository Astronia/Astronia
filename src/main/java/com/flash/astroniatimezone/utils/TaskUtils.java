package com.flash.astroniatimezone.utils;

import com.flash.astroniatimezone.Main;
import org.bukkit.Bukkit;

public class TaskUtils {

    public static void runSync(Runnable runnable) {
        if(Bukkit.isPrimaryThread())
            runnable.run();
        else
            Bukkit.getScheduler().runTask(Main.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        if(Bukkit.isPrimaryThread())
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), runnable);
        else
            runnable.run();
    }
}
