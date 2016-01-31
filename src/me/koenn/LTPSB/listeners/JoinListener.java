package me.koenn.LTPSB.listeners;

import me.koenn.LTPSB.fancyboard.FancyBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        FancyBoard board = new FancyBoard(e.getPlayer());
        board.set();
    }
}