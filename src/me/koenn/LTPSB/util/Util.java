package me.koenn.LTPSB.util;

import me.koenn.LTPSB.LTPScoreBoard;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class Util {

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static String getRoundedBalance(Player player) {
        Economy econ = LTPScoreBoard.econ;
        String balance;
        if (econ.getBalance(player) > 1000000000) {
            balance = (round(econ.getBalance(player) / 1000000000, 1) + "b").replace(".", ",");
        } else if (econ.getBalance(player) > 1000000) {
            balance = (round(econ.getBalance(player) / 1000000, 1) + "m").replace(".", ",");
        } else if (econ.getBalance(player) > 1000) {
            balance = (round(econ.getBalance(player) / 1000, 1) + "k").replace(".", ",");
        } else {
            balance = String.valueOf(econ.getBalance(player));
        }
        return balance;
    }

    public static String getRank(Player player) {
        PermissionUser user = PermissionsEx.getUser(player);
        String rank = ChatColor.stripColor(translateAlternateColorCodes('&', user.getPrefix().replace("[", "").replace("]", "")));
        if (user.getPrefix().equals("")) {
            rank = "Default";
        }
        return rank;
    }
}