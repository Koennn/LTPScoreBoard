package me.koenn.LTPSB.listeners;

import me.koenn.LTPSB.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener{

    private Main main;

    public Listeners(Main main) {
        this.main = main;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerJoin(PlayerJoinEvent e) {
        main.refreshBoard(e.getPlayer());
    }



}
