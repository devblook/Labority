package me.bryang.workity.activites;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.MathLevelsUtils;

import java.util.UUID;

public class PluginLoadingActivities implements Activities {

    private final FileManager configFile;
    private final DataLoader dataLoader;

    private final Database database;

    private final Workity workity;

    public PluginLoadingActivities(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.database = pluginCore.getDatabaseLoader().getDatabase();
        this.dataLoader = pluginCore.getDataLoader();
    }

    public void loadTask() {

        if (database.getPlayerList() == null) {
            workity.getLogger().info(" Thanks for using my plugin, don't forget check config.yml");
            return;
        }


        for (String stringToUUID : database.getPlayerList()) {
            UUID playerUniqueId = UUID.fromString(stringToUUID);

            dataLoader.createPlayerJob(playerUniqueId);

            PlayerData playerData = dataLoader.getPlayerJob(playerUniqueId);

            for (String jobName : database.getPlayerJobs(playerUniqueId)) {
                PlayerJobData playerJobData = new PlayerJobData(jobName);

                database.initActivity(playerUniqueId, false);

                playerJobData.setLevel(database.getJobIntData(jobName, "xp"));
                playerJobData.setLevel(database.getJobIntData(jobName, "level"));

                database.closeActivity();

                playerJobData.setMaxXP(
                        MathLevelsUtils.calculateNumber(configFile.getString("config.formula.max-xp"), 1));
                playerData.putJob(jobName, playerJobData);
            }
        }
    }
}
