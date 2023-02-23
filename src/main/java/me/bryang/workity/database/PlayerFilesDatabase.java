package me.bryang.workity.database;

import me.bryang.workity.Workity;
import me.bryang.workity.manager.file.FileManager;

import java.io.File;
import java.util.*;

public class PlayerFilesDatabase implements Database {


    private final Workity workity;

    private final Map<UUID, FileManager> playerList = new HashMap<>();
    List<String> listUniqueIds;

    FileManager playerFile;



    public PlayerFilesDatabase(Workity workity) {
        this.workity = workity;

    }

    @Override
    public void initDatabase() {
        File jobsFolder = new File(workity.getDataFolder().getPath() + "/players");
        jobsFolder.mkdir();
    }

    @Override
    public void createData(UUID playerUniqueId) {

        FileManager fileManager = new FileManager(workity, "players/" + playerUniqueId);
        playerList.put(playerUniqueId, fileManager);
        listUniqueIds.add(playerUniqueId.toString());

    }

    @Override
    public Database initActivity(UUID playerUniqueID, boolean toSet){
        playerFile = playerList.get(playerUniqueID);

        return this;
    }
    @Override
    public Database insertJobData(String jobName, String playerData, String newData) {
        playerFile.set("jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public Database insertJobData(String jobName, String playerData, int newData) {
        playerFile.set("jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public Database insertJobData(String jobName, String playerData, double newData) {
        playerFile.set("jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public void insertData(String playerData, int newData) {
        playerFile.set("jobs. " + playerData, newData);
    }


    @Override
    public String getJobStringData(String jobName, String playerData) {
        return playerFile.getString("jobs." + jobName + "." + playerData);
    }

    @Override
    public int getJobIntData(String jobName, String playerData) {
        return playerFile.getInt( "jobs." + jobName + "." + playerData, -1);
    }

    @Override
    public double getJobDoubleData(String jobName, String playerData) {
        return playerFile.getDouble("jobs." + jobName + "." + playerData, -1);
    }

    @Override
    public String getStringData(String playerData) {
        return playerFile.getString( playerData);
    }

    @Override
    public int getIntData(String playerData) {
        return playerFile.getInt(playerData, -1);
    }

    @Override
    public double getDoubleData(String playerData) {
        return playerFile.getDouble(playerData, -1);
    }

    @Override
    public List<String> getPlayerList() {
        return new ArrayList<>(listUniqueIds);
    }

    @Override
    public List<String> getPlayerJobs(UUID playerUniqueID) {
        return new ArrayList<>(playerList.get(playerUniqueID).getConfigurationSection(playerUniqueID + ".jobs").getKeys(false));
    }

    @Override
    public void savePlayerAndCloseActivity() {
        playerFile.save();
        playerFile = null;
    }

    @Override
    public void closeActivity(){
        playerFile = null;
    }
}
