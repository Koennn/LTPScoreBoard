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
import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Main extends JavaPlugin{

    public HashMap<String, Objective> objective = new HashMap<>();
    public HashMap<String, Score> name = new HashMap<>();
    public HashMap<String, Scoreboard> board = new HashMap<>();

    public HashMap<String, Integer> rotate = new HashMap<>();

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public void log(String msg){
        getLogger().info("[LTPScoreBoard] " + msg);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEnable() {
        log("All credits for this plugin go to Koenn");
        new ConfigManager(this.getConfig(), this);
        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getServer().getOnlinePlayers().forEach(this::refreshBoard), 1, 5 * 20);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);
    }

    public void refreshBoard(Player p){
        String pname = p.getName();
        if (rotate.get(pname) == null) {
            rotate.put(pname, 0);
        }
        PermissionUser user = PermissionsEx.getUser(p);
        String rank = ChatColor.stripColor(translateAlternateColorCodes('&', user.getPrefix().replace("(", "").replace(")", "")));
        if(user.getPrefix() == "") {
            rank = "Default";
        }
        Integer tokens = me.koenn.ltp.Main.getInstance().tokens.get(p.getUniqueId());
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board.put(pname, manager.getNewScoreboard());
        objective.put(pname, board.get(pname).registerNewObjective("info", "dummy"));
        objective.get(pname).setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.get(pname).setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "LostTimePark");
        name.clear();
        List<String> scoreboard;
        if (rotate.get(pname) == 0) {
            scoreboard = ConfigManager.getInstance().getList("scoreboard1");
            rotate.put(pname, 1);
        } else {
            scoreboard = ConfigManager.getInstance().getList("scoreboard2");
            rotate.put(pname, 0);
        }

        int line = scoreboard.size() - 1;
        for (String s : scoreboard) {
            s = s.replace("{onlinePlayers}", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
            s = s.replace("{playerName}", p.getName());
            s = s.replace("{factionLevel}", String.valueOf(LTPFactionLevels.getFactionLevel(p.getUniqueId())));
            s = s.replace("{pexRank}", rank);
            s = s.replace("{killCount}", "0");
            s = s.replace("{stattrackTokens}", String.valueOf(tokens));
            s = s.replace("{playerBalance}", "0");
            s = s.replace("{tps}", String.valueOf(round(Lag.getTPS(), 1) + "/20.0"));
            s = s.replace("?", "\u226B");
            s = translateAlternateColorCodes('&', s);
            name.put(pname, objective.get(pname).getScore(s));
            name.get(pname).setScore(line);
            line--;
        }
        p.setScoreboard(board.get(pname));
    }

    @Override
    public void onDisable(){
        log("All credits for this plugin go to Koenn");
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.setScoreboard(manager.getNewScoreboard());
        }
    }
}
