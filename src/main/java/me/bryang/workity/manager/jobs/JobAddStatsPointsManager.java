package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.data.jobs.BlockJobData;
import me.bryang.workity.data.jobs.JobData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import org.bukkit.entity.Player;

public class JobAddStatsPointsManager implements JobManager {

    private final FileDataManager playersFile;
    private final DataLoader dataLoader;


    public JobAddStatsPointsManager(PluginCore pluginCore) {
        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();

        this.dataLoader = pluginCore.getDataLoader();
    }

    @Override
    public void doWorkAction(Player player, String jobName, String itemName, PlayerJobData playerJobData) {

        JobData jobData = dataLoader.getJobDataMap().get(jobName);
        BlockJobData blockJobData = jobData.getBlockData(itemName);

        if (!jobData.isGlobalStatus()) {

            if (blockJobData.isDisableStatus()) {
                return;
            }


            int itemDataStats = playersFile.getJobData(player.getUniqueId()).getInt(".stats", -1);

            if (itemDataStats == -1) {
                playersFile.setJobData(player.getUniqueId(), "job-list." + jobData.getJobName() + ".items." + itemName + "stats.", 1);
            } else {

                playersFile.setJobData(player.getUniqueId(), "job-list." + jobData.getJobName() + ".items." + itemName + "stats", itemDataStats + 1);
            }

            playerJobData.getJobData().put(itemName, itemDataStats + 1);
            return;
        }


        int itemDataStats = playersFile.getJobData(player.getUniqueId()).getInt(".stats", -1);

        if (itemDataStats == -1) {
            playersFile.setJobData(player.getUniqueId(), "job-list." + playerJobData + "action-stats.", 1);
        } else {

            playersFile.setJobData(player.getUniqueId(), "job-list." + playerJobData + ".action-stats", itemDataStats + 1);
        }

        playerJobData.addGlobalStats();


    }
}
