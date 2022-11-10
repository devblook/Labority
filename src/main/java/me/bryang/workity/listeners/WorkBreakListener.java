package me.bryang.workity.listeners;

import me.bryang.workity.PluginCore;
import me.bryang.workity.action.Action;
import me.bryang.workity.action.JobType;
import me.bryang.workity.events.JobExecuteEvent;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class WorkBreakListener implements Listener {

    private final FileManager configFile;

    public WorkBreakListener(PluginCore pluginCore) {
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
    }

    @EventHandler
    public void onBreakJob(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            if (configFile.getBoolean("config.disable-creative")) return;
        }

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() != Material.AIR) {
            if (item.getType().name().toLowerCase().contains("pickaxe")
                    && item.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                if (configFile.getBoolean("config.disable-silk-touch")) return;
            }
        }

        Block block = event.getBlock();
        if (block.hasMetadata("workity:block-placed")) return;
        if (block.hasMetadata("workity:furnace")) return;

        Bukkit.getPluginManager().callEvent(new JobExecuteEvent(
                event.getPlayer().getUniqueId(),
                new Action(JobType.PLAYER_BREAK_BLOCK, new ItemStack(block.getType())))
        );

    }
}
