package me.bryang.workity.listeners;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.action.JobAction;
import me.bryang.workity.action.JobType;
import me.bryang.workity.events.JobExecuteEvent;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class WorkPlaceListener implements Listener {

    private final FileManager configFile;
    private final Workity workity;

    public WorkPlaceListener(PluginCore pluginCore) {
        configFile = pluginCore.getFilesLoader().getConfigFile();
        workity = pluginCore.getPlugin();
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            if (configFile.getBoolean("config.disable-creative")) {
                return;
            }
        }

        Block block = event.getBlock();
        if (block.getType().name().toLowerCase().contains("ore")) {
            block.setMetadata("workity:block-placed", new FixedMetadataValue(workity, true));
        }

        Bukkit.getPluginManager().callEvent(new JobExecuteEvent(event.getPlayer().getUniqueId(),
                new JobAction(JobType.PLAYER_PLACE_BLOCK, new ItemStack(block.getType()))));

    }
}
