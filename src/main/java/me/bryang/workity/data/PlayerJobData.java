package me.bryang.workity.data;

import java.util.HashMap;
import java.util.Map;

public class PlayerJobData {

    private final String jobName;
    private final Map<String, Integer> itemData = new HashMap<>();
    private int globalStats = 0;
    private double xpPoints = 0;
    private int maxXP = 0;
    private int level = 1;

    private double multiplier = 0;

    public PlayerJobData(String jobName) {
        this.jobName = jobName;
    }

    public String getName() {
        return jobName;
    }

    public void removeXPPoints() {
        xpPoints--;
    }

    public double getXpPoints() {
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

    public void setXPPoints(double xpPoints) {
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

    public void addGlobalStats() {
        globalStats++;
    }

    public int getGlobalStats() {
        return globalStats;
    }

    public void setGlobalStats(int globalStats) {
        this.globalStats = globalStats;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}

