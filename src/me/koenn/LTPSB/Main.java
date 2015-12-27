package me.koenn.LTPSB;

import me.koenn.LTPFL.LTPFactionLevels;
import me.koenn.LTPSB.listeners.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Main extends JavaPlugin{

    public HashMap<String, Objective> objective = new HashMap<>();
    public HashMap<String, Score> name = new HashMap<>();
    public HashMap<String, Scoreboard> board = new HashMap<>();

    public void log(String msg){
        getLogger().info("[LTPScoreBoard] " + msg);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEnable(){
        log("All credits for this plugin go to Koenn");
        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getServer().getOnlinePlayers().forEach(this::refreshBoard), 10*20, 10*20);
    }

    public void refreshBoard(Player p){
        String pname = p.getName();
        PermissionUser user = PermissionsEx.getUser(p);
        String rank = ChatColor.stripColor(translateAlternateColorCodes('&', user.getPrefix().replace("(", "").replace(")", "")));
        if(user.getPrefix() == "") {
            rank = "Default";
        }
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        log(manager.getNewScoreboard().toString());
        board.put(pname, manager.getNewScoreboard());
        objective.put(pname, board.get(pname).registerNewObjective("info", "dummy"));
        objective.get(pname).setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.get(pname).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "LTP Factions");
        log("Scoreboard initialized");
        log("Setting scoreboard...");
        name.put(pname, objective.get(pname).getScore(ChatColor.WHITE + " "));
        name.get(pname).setScore(10);
        name.put(pname, objective.get(pname).getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Info"));
        name.get(pname).setScore(9);
        name.put(pname, objective.get(pname).getScore(ChatColor.GRAY + "    Name: " + ChatColor.WHITE + p.getName()));
        name.get(pname).setScore(8);
        name.put(pname, objective.get(pname).getScore(ChatColor.GRAY + "    Level: " + ChatColor.WHITE + LTPFactionLevels.getFactionLevel(p.getUniqueId())));
        name.get(pname).setScore(7);
        name.put(pname, objective.get(pname).getScore(ChatColor.GRAY + "    Rank: " + ChatColor.WHITE + rank));
        name.get(pname).setScore(6);
        name.put(pname, objective.get(pname).getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Stats"));
        name.get(pname).setScore(5);
        name.put(pname, objective.get(pname).getScore(ChatColor.GRAY + "    Kills: " + ChatColor.WHITE + "0"));
        name.get(pname).setScore(4);
        name.put(pname, objective.get(pname).getScore(ChatColor.GRAY + "    Deaths: " + ChatColor.WHITE + "0"));
        name.get(pname).setScore(3);
        p.setScoreboard(board.get(pname));
        log("Set!");
    }

    @Override
    public void onDisable(){
        log("All credits for this plugin go to Koenn");
    }
}
