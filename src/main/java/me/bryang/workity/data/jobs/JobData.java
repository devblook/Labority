package me.bryang.workity.data.jobs;

import me.bryang.workity.action.JobType;

import java.util.Map;

public class JobData {

    private final String jobName;

    private final Map<String, BlockJobData> blockJobDataMap;
    private final JobType activityType;


    public JobData(String jobName, JobType activityType,
                   Map<String, BlockJobData> blockJobDataMap) {

        this.jobName = jobName;

        this.activityType = activityType;
        this.blockJobDataMap = blockJobDataMap;
    }

    public boolean isActivityType(JobType activityType) {
        return this.activityType == activityType;
    }

    public String getJobName() {
        return jobName;
    }

    public Map<String, BlockJobData> getBlockJobDataMap() {
        return blockJobDataMap;
    }

    public BlockJobData getBlockData(String blockName) {
        return blockJobDataMap.get(blockName);
    }


}
