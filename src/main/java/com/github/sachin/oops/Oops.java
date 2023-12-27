package com.github.sachin.oops;

import com.jeff_media.morepersistentdatatypes.DataType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Oops extends JavaPlugin implements Listener {

    private static Oops plugin;


    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(this,this);
        getCommand("oops").setExecutor(new Commands());
        reloadConfigs();
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&e[&6Oops&e] &aConfig loaded successfully"));
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItemInHand().clone();
        item.setAmount(1);
        if(matchString(item.getType().toString(),getConfig().getStringList("black-list-materials"))) return;
        CustomBlockData data = new CustomBlockData(e.getBlockPlaced().getLocation());
        data.set(OConstants.PLACER_KEY, DataType.UUID,player.getUniqueId());
        data.set(OConstants.ITEM_KEY,DataType.ITEM_STACK,item);

        data.set(OConstants.TIME_KEY, PersistentDataType.LONG,System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        Player player = e.getPlayer();
        if(getConfig().getBoolean("check-permission") && !player.hasPermission("oops.use.undo")) return;
        CustomBlockData data = new CustomBlockData(e.getClickedBlock().getLocation());
        if(data.has(OConstants.TIME_KEY,PersistentDataType.LONG)){
            long placedTime = data.get(OConstants.TIME_KEY,PersistentDataType.LONG);
            long currentTime = System.currentTimeMillis();
            UUID uuid = data.get(OConstants.PLACER_KEY,DataType.UUID);
            ItemStack item = data.get(OConstants.ITEM_KEY,DataType.ITEM_STACK);
            boolean timeout = currentTime-placedTime < (getConfig().getLong("time-out",2)*1000);
            if(!timeout){clearBlockData(data);}
            if(!uuid.equals(player.getUniqueId())) return;
            if(timeout){
                e.getClickedBlock().setType(Material.AIR);
                e.getPlayer().getWorld().dropItem(e.getClickedBlock().getLocation().add(.5,.5,.5),item);

            }
        }
    }

    public void clearBlockData(CustomBlockData data){
        data.remove(OConstants.TIME_KEY);
        data.remove(OConstants.ITEM_KEY);
        data.remove(OConstants.PLACER_KEY);
    }

    public boolean matchString(String str, List<String> matcher){
        for(String s : matcher){
            if(s.startsWith("^") && str.startsWith(s.replace("^", ""))){
                return true;
            }
            if(s.endsWith("$") && str.endsWith(s.replace("$", ""))){
                return true;
            }
            if(str.equals(s)) return true;

        }
        return false;
    }
    public void reloadConfigs(){
        saveDefaultConfig();
        try {
            ConfigUpdater.update(plugin, "config.yml", new File(getDataFolder(), "config.yml"), new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();

    }

    public static Oops getPlugin() {
        return plugin;
    }

    public static NamespacedKey getKey(String key){
        return new NamespacedKey(plugin,key);
    }

}
