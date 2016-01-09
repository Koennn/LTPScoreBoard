package me.koenn.LTPSB.listeners;

import me.koenn.LTPSB.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {

    private Main main;

    public Listeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        main.refreshBoard(e.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        main.deaths.put(e.getEntity(), main.deaths.get(e.getEntity()));
        if (e.getEntity().getKiller() != null) {
            main.kills.put(e.getEntity().getKiller(), main.kills.get(e.getEntity().getKiller()));
        }
    }
}
