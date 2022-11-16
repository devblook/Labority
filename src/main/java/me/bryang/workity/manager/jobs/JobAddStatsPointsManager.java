package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.data.jobs.BlockJobData;
import me.bryang.workity.data.jobs.JobData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import org.bukkit.entity.Player;

public class JobAddStatsPointsManager implements JobManager {

    private final Database database;
    private final DataLoader dataLoader;


    public JobAddStatsPointsManager(PluginCore pluginCore) {
        this.database = pluginCore.getDatabaseLoader().getDatabase();

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


            int itemDataStats = database.getJobIntData(player.getUniqueId(), jobName, "stats.item." + itemName);

            if (itemDataStats == -1) {
                database.insertJobData(player.getUniqueId(), jobName, "stats.item." + itemName, 1);

            } else {
                database.insertJobData(player.getUniqueId(), jobName, "stats.item." + itemName, itemDataStats + 1);
            }

            database.save();
            playerJobData.getJobData().put(itemName, itemDataStats + 1);
            return;
        }


        int itemDataStats = database.getIntData(player.getUniqueId(), "stats");

        if (itemDataStats == -1) {
            database.insertData(player.getUniqueId(), "stats", 1);
        } else {

            database.insertData(player.getUniqueId(), "stats", itemDataStats + 1);
        }

        database.save();
        playerJobData.addGlobalStats();


    }
}
