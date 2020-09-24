package com.flash.astroniatimezone.utils.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerUtils {

    @Getter private static final Map<String, UUIDPair> pairs = new HashMap<>();

    public static UUIDPair getExtraInfo(String name) {
        if(pairs.containsKey(name.toLowerCase())){
            return pairs.get(name.toLowerCase());
        }

        Player target = Bukkit.getPlayer(name);
        if(target != null && target.isOnline()) {
            return new UUIDPair(target.getUniqueId(), target.getName());
        }

        OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(name);
        if(offlineTarget.hasPlayedBefore()) {
            return new UUIDPair(offlineTarget.getUniqueId(), offlineTarget.getName());
        }

        UUID uuid = UUIDUtil.getUUIDFromName(name);
        if (uuid != null) {
            return new UUIDPair(uuid, name);
        }

        return null;
    }
}
