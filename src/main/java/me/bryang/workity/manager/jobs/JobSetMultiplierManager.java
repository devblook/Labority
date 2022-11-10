package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.VaultHookManager;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.entity.Player;

public class JobSetMultiplierManager implements WorkAction {

    private final DataLoader dataLoader;

    private final FileManager configFile;

    private final VaultHookManager vaultHookManager;

    public JobSetMultiplierManager(PluginCore pluginCore) {

        this.dataLoader = pluginCore.getDataLoader();

        this.configFile = pluginCore.getFilesLoader().getConfigFile();

        this.vaultHookManager = pluginCore.getManagerLoader().getVaultHookManager();
    }

    @Override
    public void doWorkAction(Player player, String jobName, String itemName, PlayerJobData playerJobData) {
        double multiplier;


        if (vaultHookManager.getPermission().hasGroupSupport()) {
            if (!configFile.isInt("config.multiplier.group." +
                    vaultHookManager.getPermission().getPrimaryGroup(player))) {

                multiplier = configFile.getInt("config.multiplier.default");

            } else {
                multiplier = configFile.getInt("config.multiplier.group." +
                        vaultHookManager.getPermission().getPrimaryGroup(player));
            }
        } else {
            multiplier = configFile.getInt("config.multiplier.default");
        }

        if (dataLoader.getServerMultiplier() > 0) {
            multiplier = multiplier + dataLoader.getServerMultiplier();
        }

        playerJobData.setMultiplier(multiplier);
    }
}
