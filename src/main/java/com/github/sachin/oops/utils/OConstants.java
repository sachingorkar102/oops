package com.github.sachin.oops.utils;

import com.github.sachin.oops.Oops;
import org.bukkit.NamespacedKey;

public class OConstants {


    public static final NamespacedKey PLACER_KEY = Oops.getKey("player_who_placed_the_block");
    public static final NamespacedKey ITEM_KEY = Oops.getKey("item_used_to_place_the_block");
    public static final NamespacedKey TIME_KEY = Oops.getKey("time_when_the_block_is_placed");
    public static final NamespacedKey BLOCK_KEY = Oops.getKey("block_data_of_previous_block");

    public static final NamespacedKey OOPS_NON_USER_KEY = Oops.getKey("player_not_having_oops_enabled");


    public static final String RELOAD_COMMAND_PERM = "oops.command.reload";
    public static final String TOGGLE_COMMAND_PERM = "oops.command.toggle";

    public static final String USE_OOPS_PERM = "oops.use.undo";
}
