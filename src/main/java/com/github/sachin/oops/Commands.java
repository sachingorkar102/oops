package com.github.sachin.oops;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1) return true;

        if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("oops.command.reload")){
            Oops.getPlugin().reloadConfigs();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e[&6Oops&e] &aConfig reloaded successfully"));
        }
        return true;
    }
}
