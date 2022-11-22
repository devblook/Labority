package me.bryang.workity.loader;

import me.bryang.workity.Workity;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.data.jobs.JobData;

import java.util.*;


public class DataLoader implements Loader {

    private final Map<UUID, PlayerData> playerJobDataMap = new HashMap<>();

    private final Map<String, JobData> jobDataMap = new HashMap<>();
    private final Set<String> jobStatusSet = new HashSet<>();

    private final Workity workity;
    private double serverMultiplier;

    public DataLoader(Workity workity) {
        this.workity = workity;
    }

    @Override
    public void load() {
        serverMultiplier = 0;
        workity.getLogger().info(" Data loaded.");

    }

    public void createPlayerJob(UUID uuid) {
        playerJobDataMap.put(uuid, new PlayerData());
    }

    public PlayerData getPlayerJob(UUID uuid) {
        return playerJobDataMap.get(uuid);
    }

    public JobData getJob(String jobName) {
        return jobDataMap.get(jobName);
    }

    public boolean jobExists(String jobName) {
        return jobDataMap.containsKey(jobName);
    }

    public Set<String> getJobStatusSet() {
        return jobStatusSet;
    }

    public double getServerMultiplier() {
        return serverMultiplier;
    }

    public void setServerMultiplier(double serverMultiplier) {
        this.serverMultiplier = serverMultiplier;
    }

    public Map<String, JobData> getJobDataMap() {
        return jobDataMap;
    }


}
