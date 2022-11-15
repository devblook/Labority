package me.bryang.workity.activites;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.data.jobs.JobData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.ConvertFileToMapManager;

import java.io.File;
import java.util.Map;

public class JobsLoadingActivities implements Activities {


    private final DataLoader dataLoader;

    private final Workity workity;

    private final ConvertFileToMapManager convertFileToMapManager;

    public JobsLoadingActivities(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();

        this.dataLoader = pluginCore.getDataLoader();

        this.convertFileToMapManager = pluginCore.getManagerLoader().getConvertFileToMapManager();
    }

    public void loadTask() {
        File jobsFolder = new File(workity.getDataFolder().getPath() + "/jobs");

        Map<String, JobData> jobDataMap = dataLoader.getJobDataMap();

        for (String fileName : jobsFolder.list()) {

            JobData jobData = convertFileToMapManager.convert(fileName);

            jobDataMap.put(jobData.getJobName(), jobData);
        }

    }
}
