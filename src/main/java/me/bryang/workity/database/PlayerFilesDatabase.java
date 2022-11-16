package me.bryang.workity.database;

import me.bryang.workity.Workity;
import me.bryang.workity.manager.file.FileManager;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerFilesDatabase implements Database{

    private final FileManager fileManager;

    public PlayerFilesDatabase(Workity workity){
        fileManager = new FileManager(workity, "players");
    }

    @Override
    public Database insertJobData(UUID playerUniqueId, String jobName, String playerData, String newData) {
        fileManager.set("players." + playerUniqueId + ".jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public Database insertJobData(UUID playerUniqueId, String jobName, String playerData, int newData) {
        fileManager.set("players." + playerUniqueId + ".jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public Database insertJobData(UUID playerUniqueId, String jobName, String playerData, double newData) {
        fileManager.set("players." + playerUniqueId + ".jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public void insertData(UUID playerUniqueId, String playerData, int newData) {
        fileManager.set("players." + playerUniqueId + ".jobs. " + playerData, newData);
    }


    @Override
    public String getJobStringData(UUID playerUniqueId, String jobName, String playerData) {
        return fileManager.getString("players." + playerUniqueId + ".jobs." + jobName + "." + playerData);
    }

    @Override
    public int getJobIntData(UUID playerUniqueId, String jobName, String playerData) {
        return fileManager.getInt("players." + playerUniqueId + ".jobs." + jobName + "." + playerData, -1);
    }

    @Override
    public double getJobDoubleData(UUID playerUniqueId, String jobName, String playerData) {
        return fileManager.getDouble("players." + playerUniqueId + ".jobs." + jobName + "." + playerData, -1);
    }

    @Override
    public String getStringData(UUID playerUniqueId, String playerData) {
        return fileManager.getString("players." + playerUniqueId + "." + playerData);
    }

    @Override
    public int getIntData(UUID playerUniqueId, String playerData) {
        return fileManager.getInt("players." + playerUniqueId + "." + playerData, -1);
    }

    @Override
    public double getDoubleData(UUID playerUniqueId, String playerData) {
        return fileManager.getDouble("players." + playerUniqueId + "." + playerData, -1);
    }

    @Override
    public List<String> getPlayerList() {
        return new ArrayList<>(fileManager.getConfigurationSection("players").getKeys(false));
    }

    @Override
    public List<String> getPlayerJobs(UUID playerUniqueID) {
        return new ArrayList<>(fileManager.getConfigurationSection("players." + playerUniqueID + ".jobs").getKeys(false));
    }

    @Override
    public void save() {
        fileManager.save();
    }
}
