package com.flash.astroniatimezone.command;

import com.flash.astroniatimezone.Main;
import com.flash.astroniatimezone.api.chat.C;
import com.flash.astroniatimezone.api.command.Command;
import com.flash.astroniatimezone.api.command.CommandData;
import com.flash.astroniatimezone.game.player.AstroniaProfile;
import com.flash.astroniatimezone.utils.Locale;
import com.flash.astroniatimezone.utils.player.PlayerUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CaneTopCommand {

    @Command(label = "canetop", aliases = {"ctop"}, permission = "astronia.canetop", playerOnly = false)
    public void onCommand(CommandData data) {
        CommandSender sender = data.getSender();

        List<Map.Entry<UUID, Integer>> values = sortByValue(Main.getInstance().getBackend().getTopTenSugarcane());

        sender.sendMessage(Locale.SUGAR_CANE_TOP_INTRODUCTION.toString());
        for (int i = 0; i < 10; i++) {
            if (values.size() > i)
                sender.sendMessage(Locale.SUGAR_CANE_LEADERBOARD_MESSAGE.toString()
                        .replace("%number%", Integer.toString(i) + 1)
                        .replace("%name%", AstroniaProfile.getByUuid(values.get(i).getKey()).getName())
                        .replace("%value%", Integer.toString(values.get(i).getValue())));
        }
    }

    static <K,V extends Comparable<? super V>>
    List<Map.Entry<K, V>> sortByValue(Map<K,V> map) {

        List<Map.Entry<K,V>> sortedEntries = new ArrayList<Map.Entry<K,V>>(map.entrySet());

        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;
    }
}
