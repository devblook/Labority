package me.bryang.workity.manager;

import me.bryang.workity.Workity;
import me.bryang.workity.action.JobType;
import me.bryang.workity.data.jobs.BlockJobData;
import me.bryang.workity.data.jobs.JobData;
import me.bryang.workity.manager.file.FileManager;

import java.util.HashMap;
import java.util.Map;

public class ConvertFileToMapManager {

    private final Workity workity;


    public ConvertFileToMapManager(Workity workity) {
        this.workity = workity;
    }


    public JobData convert(String filePath) {
        FileManager fileManager = new FileManager(workity, filePath);

        String name = fileManager.getString("job.name");
        JobType activityType = JobType.valueOf(fileManager.getString("job.type").toUpperCase());

        Map<String, BlockJobData> materialBlockJobDataMap = new HashMap<>();

        for (String keys : fileManager.getConfigurationSection("job.items").getKeys(false)) {

            int money = fileManager.getInt("job.items." + keys + ".money");
            int xp = fileManager.getInt("job.items." + keys + ".xp");


            materialBlockJobDataMap.put(keys, new BlockJobData(money, xp));
        }

        return new JobData(name, activityType, materialBlockJobDataMap);
    }
}
