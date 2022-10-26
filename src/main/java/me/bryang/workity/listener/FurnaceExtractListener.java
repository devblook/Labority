package me.bryang.workity.listener;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class FurnaceExtractListener implements Listener {

    private final Workity workity;
    private final FileManager configFile;
    private final FileDataManager playersFile;

    public FurnaceExtractListener(PluginCore pluginCore) {

        workity = pluginCore.getPlugin();

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();
    }

    public void onFurnace(FurnaceExtractEvent furnaceExtractEvent) {

        if (furnaceExtractEvent.getBlock().getType() == Material.STONE) {
            for (String keys : configFile.getConfigurationSection("jobs").getKeys(false)) {

                if (!configFile.getString("config." + keys + ".type")
                        .equalsIgnoreCase("PLAYER_BREAK_BLOCK")) {
                    continue;
                }

                if (!playersFile.getJobsKeys(furnaceExtractEvent.getPlayer().getUniqueId()).contains(keys)) {
                    continue;
                }

                if (!configFile.getConfigurationSection("jobs." + keys + ".items").contains("STONE")) {
                    continue;
                }

                furnaceExtractEvent.getBlock().setMetadata("workity::furnace",
                        new FixedMetadataValue(workity, true));


            }
        }
    }
}
