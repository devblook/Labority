package me.bryang.workity.data.job;

import me.bryang.workity.action.JobType;

import java.util.Map;

public class JobData {

    private final String jobName;

    private final Map<String, BlockJobData> blockJobDataMap;
    private final JobType activityType;

    private final boolean globalStatus;


    public JobData(String jobName, boolean globalStatus, JobType activityType,
                   Map<String, BlockJobData> blockJobDataMap) {

        this.jobName = jobName;

        this.globalStatus = globalStatus;
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

    public boolean isGlobalStatus() {
        return globalStatus;
    }

}
