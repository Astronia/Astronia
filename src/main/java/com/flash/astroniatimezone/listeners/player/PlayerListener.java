package com.flash.astroniatimezone.listeners.player;

import com.flash.astroniatimezone.game.player.AstroniaProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AstroniaProfile profile = AstroniaProfile.getByPlayer(player);

        profile.setName(event.getPlayer().getName());

        profile.save();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        this.handleDisconnect(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onKick(PlayerKickEvent event) {
        this.handleDisconnect(event.getPlayer());
    }

    private void handleDisconnect(Player p) {
        AstroniaProfile profile = AstroniaProfile.getByPlayer(p);

        p.closeInventory();

        profile.save();
        AstroniaProfile.getProfiles().remove(profile.getUuid().toString());
    }
}
