package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.loader.database.DatabaseType;
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

        database.initActivity(player.getUniqueId(), true);

        int itemDataStats = database.getJobIntData(jobName, "stats.item." + itemName);

        if (itemDataStats == -1) {
            database.insertJobData(jobName, "stats.item." + itemName, 1);

        } else {
            database.insertJobData(jobName, "stats.item." + itemName, itemDataStats + 1);
        }

        playerJobData.getJobData().put(itemName, itemDataStats + 1);


        int globalStats = database.getIntData("stats");

        if (globalStats == -1) {
            database.insertData("stats", 1);
        } else {

            database.insertData("stats", globalStats + 1);
        }

        database.savePlayerAndCloseActivity();
        playerJobData.addGlobalStats();


    }
}
