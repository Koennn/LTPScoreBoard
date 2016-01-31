package me.koenn.LTPSB.util;

import me.koenn.LTPFL.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class PlaceholderUtil {

    public static String replaceAllPlaceholders(String oldString, Player p) {
        oldString = oldString.replace("{onlinePlayers}", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
        oldString = oldString.replace("{playerName}", p.getName());
        oldString = oldString.replace("{factionLevel}", String.valueOf(PlayerUtil.getFactionsPlayer(p).getLevel()));
        oldString = oldString.replace("{pexRank}", Util.getRank(p));
        oldString = oldString.replace("{killCount}", String.valueOf(0));
        oldString = oldString.replace("{deathCount}", String.valueOf(0));
        oldString = oldString.replace("{voteCount}", "0");
        oldString = oldString.replace("{playerBalance}", "$" + Util.getRoundedBalance(p));
        oldString = oldString.replace("{tps}", String.valueOf(Util.round(Lag.getTPS(), 1) + "/20.0"));
        oldString = oldString.replace("?", "\u2726");
        oldString = oldString.replace("™", "\u2122");
        return translateAlternateColorCodes('&', oldString);
    }
}
