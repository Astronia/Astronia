package com.flash.astroniatimezone.listeners.player;

import com.flash.astroniatimezone.game.player.AstroniaProfile;
import com.flash.astroniatimezone.game.player.data.AstroniaStatistic;
import com.flash.astroniatimezone.game.player.data.StatisticType;
import dev.norska.scyther.api.ScytherAPI;
import dev.norska.scyther.api.ScytherAutocollectEvent;
import dev.norska.scyther.api.ScytherAutosellEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ScytherListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SUGAR_CANE_BLOCK)
            return;

        if (event.getPlayer().getItemOnCursor() != null && ScytherAPI.isHarvesterItem(event.getPlayer().getInventory().getItemInMainHand()))
            return;

        int amount = 0;
        Location loc = event.getBlock().getLocation().clone();

        for (int i = 0; i < 4; i++) {
            if (loc.add(0, i, 0).getBlock().getType() == Material.SUGAR_CANE_BLOCK)
                amount++;
        }

        StatisticType type = StatisticType.getByMaterial(Material.SUGAR_CANE_BLOCK);
        AstroniaProfile profile = AstroniaProfile.getByPlayer(event.getPlayer());
        AstroniaStatistic statisticObj = profile.getStatistic(type);
        if(statisticObj != null) {
            for (int i = 0; i < amount; i++) {
                profile.updateStatistic(statisticObj.Increment());
            }

            profile.save();
        }
    }

    @EventHandler
    public void onAutoSell(ScytherAutosellEvent event) {
        StatisticType type = StatisticType.getByMaterial(event.getCrop().getType());
        if (type == null)
            return;

        AstroniaProfile profile = AstroniaProfile.getByPlayer(event.getPlayer());
        AstroniaStatistic statisticObj = profile.getStatistic(type);
        if(statisticObj != null) {
            for (int i = 0; i < event.getAmount(); i++) {
                profile.updateStatistic(statisticObj.Increment());
            }

            profile.save();
        }
    }

    //TODO: Handle Drop Multiplier

    @EventHandler
    public void onAutoCollect(ScytherAutocollectEvent event) {
        StatisticType type = StatisticType.getByMaterial(event.getCrop().getType());
        if (type == null)
            return;

        AstroniaProfile profile = AstroniaProfile.getByPlayer(event.getPlayer());
        AstroniaStatistic statisticObj = profile.getStatistic(type);
        if(statisticObj != null) {
            for (int i = 0; i < event.getDropAmount(); i++) {
                profile.updateStatistic(statisticObj.Increment());
            }

            profile.save();
        }
    }
}
