package me.bryang.workity.task;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.JobData;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.TextUtils;

import java.util.UUID;

public class PluginLoadingTask {

    private final FileManager configFile;
    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public PluginLoadingTask(PluginCore pluginCore) {

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();

        this.dataLoader = pluginCore.getDataLoader();

    }

    public void loadTask() {

        if (playersFile.getPlayersKeys() == null) {
            System.out.println("[Workity] Thanks for using my plugin, don't forget check config.yml");
            return;
        }

        for (String stringToUUID : playersFile.getPlayersKeys()) {

            UUID playerUniqueId = UUID.fromString(stringToUUID);

            dataLoader.createPlayerJob(playerUniqueId);

            PlayerData playerData = dataLoader.getPlayerJob(playerUniqueId);

            for (String jobName : playersFile.getJobsKeys(playerUniqueId)) {

                JobData jobData = new JobData(jobName);

                jobData.setLevel(playersFile.getJobData(playerUniqueId).getInt(".job-list." + jobName + ".xp"));
                jobData.setLevel(playersFile.getJobData(playerUniqueId).getInt(".job-list." + jobName + ".level"));
                jobData.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), 1));

                playerData.addJobData(jobName, jobData);
            }
        }
    }
}
