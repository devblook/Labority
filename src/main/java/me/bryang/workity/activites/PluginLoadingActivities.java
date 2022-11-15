package me.bryang.workity.activites;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.MathLevelsUtils;

import java.util.UUID;

public class PluginLoadingActivities implements Activities {

    private final FileManager configFile;
    private final FileDataManager playersFile;
    private final DataLoader dataLoader;
    private final Workity workity;

    public PluginLoadingActivities(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();
        this.dataLoader = pluginCore.getDataLoader();
    }

    public void loadTask() {

        if (playersFile.getPlayersKeys() == null) {
            workity.getLogger().info(" Thanks for using my plugin, don't forget check config.yml");
            return;
        }


        for (String stringToUUID : playersFile.getPlayersKeys()) {
            UUID playerUniqueId = UUID.fromString(stringToUUID);
            dataLoader.createPlayerJob(playerUniqueId);
            PlayerData playerData = dataLoader.getPlayerJob(playerUniqueId);

            for (String jobName : playersFile.getJobsKeys(playerUniqueId)) {
                PlayerJobData playerJobData = new PlayerJobData(jobName);
                playerJobData.setLevel(playersFile.getJobData(playerUniqueId).getInt(".job-list." + jobName + ".xp"));
                playerJobData.setLevel(playersFile.getJobData(playerUniqueId).getInt(".job-list." + jobName + ".level"));
                playerJobData.setMaxXP(
                        MathLevelsUtils.calculateNumber(configFile.getString("config.formula.max-xp"), 1));
                playerData.putJob(jobName, playerJobData);
            }
        }
    }
}
