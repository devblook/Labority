package me.bryang.workity.listeners;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class WorkFurnaceListener implements Listener {

    private final Workity workity;
    private final FileManager configFile;
    private final Database database;

    private final DataLoader dataLoader;

    public WorkFurnaceListener(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.database = pluginCore.getDatabaseLoader().getDatabase();

        this.dataLoader = pluginCore.getDataLoader();
    }

    @EventHandler
    private void onFurnaceJob(FurnaceExtractEvent event) {
        if (event.getBlock().getType() != Material.STONE) return;

        for (String keys : configFile.getConfigurationSection("jobs").getKeys(false)) {
            if (!configFile.getString("config." + keys + ".type")
                    .equalsIgnoreCase("PLAYER_BREAK_BLOCK")) {
                continue;
            }
            if (!database.getPlayerJobs(event.getPlayer().getUniqueId()).contains(keys)) {
                continue;
            }

            if (!dataLoader.getJob(keys).getBlockJobDataMap().containsKey("STONE")) {
                continue;
            }

            event.getBlock().setMetadata("workity::furnace", new FixedMetadataValue(workity, true));
        }
    }
}
