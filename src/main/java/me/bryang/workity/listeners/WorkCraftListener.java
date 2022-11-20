package me.bryang.workity.listeners;

import me.bryang.workity.PluginCore;
import me.bryang.workity.action.JobAction;
import me.bryang.workity.action.JobType;
import me.bryang.workity.events.JobExecuteEvent;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class WorkCraftListener implements Listener {


    @EventHandler
    public void playerCraftItem(CraftItemEvent event) {

        Bukkit.getPluginManager().callEvent(new JobExecuteEvent(event.getWhoClicked().getUniqueId(),
                new JobAction(JobType.PLAYER_CRAFT_ITEM, event.getRecipe().getResult())));
    }
}
