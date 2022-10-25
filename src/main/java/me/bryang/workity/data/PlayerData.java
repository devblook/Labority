package me.bryang.workity.data;

import java.util.HashMap;
import java.util.Map;

public class PlayerData {

    private final Map<String, JobData> jobDataMap = new HashMap<>();

    public void addJob(String jobName) {
        jobDataMap.put(jobName, new JobData(jobName));
    }

    public void putJob(String jobName, JobData jobData) {
        jobDataMap.put(jobName, jobData);
    }

    public JobData getJob(String jobName) {
        return jobDataMap.get(jobName);
    }

    public void removeJob(String jobName) {
        jobDataMap.remove(jobName);
    }

    public boolean hasTheJob(String jobName) {
        return jobDataMap.containsKey(jobName);
    }

    public int getJobSize() {
        return jobDataMap.size();
    }

    public Map<String, JobData> getJobsMap() {
        return jobDataMap;
    }
}
