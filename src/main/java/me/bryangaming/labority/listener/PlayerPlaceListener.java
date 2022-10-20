package me.bryangaming.labority.listener;

import me.bryangaming.labority.action.Action;
import me.bryangaming.labority.action.JobType;
import me.bryangaming.labority.event.JobsEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPlaceListener implements Listener {

    @EventHandler
    public void onBreak(BlockPlaceEvent event){

        Block block = event.getBlock();

        Bukkit.getPluginManager().callEvent(new JobsEvent(event.getPlayer().getUniqueId(),
                new Action(JobType.PLAYER_PLACE_BLOCK, new ItemStack(block.getType()))));

    }
}
