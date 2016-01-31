package me.koenn.LTPSB;

import me.koenn.LTPSB.fancyboard.FancyBoard;
import me.koenn.LTPSB.listeners.JoinListener;
import me.koenn.LTPSB.util.ConfigManager;
import me.koenn.LTPSB.util.Lag;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class LTPScoreBoard extends JavaPlugin {

    public static Economy econ = null;

    public void log(String msg) {
        getLogger().info(String.format("[%s] " + msg, this.getDescription().getName()));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEnable() {
        log("All credits for this plugin go to Koenn");
        if (!(setupEconomy())) {
            log("Disabled due to no Vault dependency found!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        FancyBoard.pl = this;
        new ConfigManager(this.getConfig(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100, 1);
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {
        log("All credits for this plugin go to Koenn");
    }
}
