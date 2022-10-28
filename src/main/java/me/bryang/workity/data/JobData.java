package me.bryang.workity.data;

import java.util.HashMap;
import java.util.Map;

public class JobData {

    private final String jobName;
    private int xpPoints = 0;
    private int maxXP = 0;
    private int level = 1;

    public JobData(String jobName) {
        this.jobName = jobName;
    }

    private final Map<String, Integer> itemData = new HashMap<>();
    int globalStats = 0;

    public String getName() {
        return jobName;
    }

    public void removeXPPoints() {
        xpPoints--;
    }

    public int getXpPoints() {
        return xpPoints;
    }

    public void addLevel() {
        level++;
    }

    public void removeLevel() {
        level--;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXPPoints(int xpPoints) {
        this.xpPoints = xpPoints;
    }

    public int getMaxXP() {
        return maxXP;
    }

    public void setMaxXP(int maxXP) {
        this.maxXP = maxXP;
    }

    public Map<String, Integer> getJobData() {
        return itemData;
    }

    public void setGlobalStats(int globalStats) {
        this.globalStats = globalStats;
    }

    public void addGlobalStats() {
        globalStats++;
    }

    public int getGlobalStats() {
       return globalStats;
    }
}

