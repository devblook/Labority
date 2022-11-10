package me.bryang.workity.activites;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.action.JobType;
import me.bryang.workity.data.job.BlockJobData;
import me.bryang.workity.data.job.JobData;
import me.bryang.workity.interfaces.Activities;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JobsLoadingActivities implements Activities {


    private final DataLoader dataLoader;
    private final Workity workity;

    public JobsLoadingActivities(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();

        this.dataLoader = pluginCore.getDataLoader();
    }

    public void loadTask() {
        File jobsFolder = new File(workity.getDataFolder().getPath() + "/jobs");

        Map<String, JobData> jobDataMap = dataLoader.getJobDataMap();

        for (String fileName : jobsFolder.list()) {

            FileManager fileManager = new FileManager(workity, fileName);

            String name = fileManager.getString("job.name");
            boolean globalStats = fileManager.getBoolean("job.global-status");

            JobType activityType = JobType.valueOf(fileManager.getString("job.type").toUpperCase());

            Map<String, BlockJobData> materialBlockJobDataMap = new HashMap<>();

            for (String keys : fileManager.getConfigurationSection("job.items").getKeys(false)) {

                int money = fileManager.getInt("job.items." + keys + ".money");
                int xp = fileManager.getInt("job.items." + keys + ".xp");

                boolean enableStatus = fileManager.getBoolean("job.items." + keys + ".enable-status");

                materialBlockJobDataMap.put(keys, new BlockJobData(money, xp, enableStatus));
            }

            JobData jobData = new JobData(name, globalStats, activityType, materialBlockJobDataMap);
            jobDataMap.put(name, jobData);
        }
    }
}
