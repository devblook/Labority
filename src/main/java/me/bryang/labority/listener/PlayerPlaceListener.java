package me.bryang.labority.listener;

import me.bryang.labority.Labority;
import me.bryang.labority.PluginCore;
import me.bryang.labority.action.Action;
import me.bryang.labority.action.JobType;
import me.bryang.labority.events.JobsEvent;
import me.bryang.labority.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerPlaceListener implements Listener {

    private final FileManager configFile;
    private final Labority labority;

    public PlayerPlaceListener(PluginCore pluginCore) {
        configFile = pluginCore.getFilesLoader().getConfigFile();
        labority = pluginCore.getPlugin();
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {

        if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            if (configFile.getBoolean("config.disable-creative")) {
                return;
            }
        }

        Block block = event.getBlock();

        if (block.getType().name().toLowerCase().contains("ore")) {
            block.setMetadata("workity:block-placed", new FixedMetadataValue(labority, true));
        }

        Bukkit.getPluginManager().callEvent(new JobsEvent(event.getPlayer().getUniqueId(),
                new Action(JobType.PLAYER_PLACE_BLOCK, new ItemStack(block.getType()))));

    }
}
