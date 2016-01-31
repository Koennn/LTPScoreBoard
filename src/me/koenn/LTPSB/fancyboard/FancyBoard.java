package me.koenn.LTPSB.fancyboard;

import me.koenn.LTPSB.util.ConfigManager;
import me.koenn.LTPSB.util.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class FancyBoard {

    public static Plugin pl;

    private Scoreboard scoreboard;
    private Objective objective;
    private Player player;
    private int task;

    public FancyBoard(Player player) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.player = player;
        this.refreshBoard(false);
    }

    public void refreshBoard(boolean unregister) {
        if (!this.player.isOnline()) {
            Bukkit.getScheduler().cancelTask(this.task);
            return;
        }
        if (unregister) {
            this.objective.unregister();
        }
        this.objective = this.scoreboard.registerNewObjective("info", "dummy");
        this.objective.setDisplayName(translateAlternateColorCodes('&', "&8> &6LostTimePark &8<"));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        List<String> board = ConfigManager.getInstance().getList("scoreboard1");
        int index = board.size() - 1;
        for (String s : board) {
            this.objective.getScore(PlaceholderUtil.replaceAllPlaceholders(s, this.player)).setScore(index);
            index--;
        }
    }

    public void set() {
        this.player.setScoreboard(this.scoreboard);
        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> this.refreshBoard(true), 0, 50);
    }
}
