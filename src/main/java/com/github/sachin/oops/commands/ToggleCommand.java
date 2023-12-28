package com.github.sachin.oops.commands;

import com.github.sachin.oops.utils.OConstants;
import com.github.sachin.oops.Oops;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class ToggleCommand extends BukkitCommand {

    private final Oops plugin;
    public ToggleCommand(Oops plugin) {
        super("toggleundo");
        this.plugin = plugin;
        setAliases(plugin.getConfig().getStringList("command-aliases"));
        setPermission(OConstants.TOGGLE_COMMAND_PERM);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission(OConstants.TOGGLE_COMMAND_PERM)){
                if(player.getPersistentDataContainer().has(OConstants.OOPS_NON_USER_KEY, PersistentDataType.INTEGER)){
                    player.getPersistentDataContainer().remove(OConstants.OOPS_NON_USER_KEY);
                    player.sendMessage(plugin.getMessage("messages.oops-use-enabled"));
                }
                else{
                    player.getPersistentDataContainer().set(OConstants.OOPS_NON_USER_KEY,PersistentDataType.INTEGER,1);
                    player.sendMessage(plugin.getMessage("messages.oops-use-disabled"));
                }
            }
        }
        return true;
    }
}
