package me.bryang.workity.database;

import me.bryang.workity.Workity;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.entity.Player;
import org.checkerframework.checker.guieffect.qual.UI;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PlayerFilesDatabase implements Database{


    private final Workity workity;

    private final Map<UUID, FileManager> playerList = new HashMap<>();
    List<String> listUniqueIds;


    public PlayerFilesDatabase(Workity workity){
        this.workity = workity;

        initDatabase();
    }

    @Override
    public void initDatabase(){
        File jobsFolder = new File(workity.getDataFolder().getPath() + "/players");
        jobsFolder.mkdir();
    }

    @Override
    public void createData(UUID playerUniqueId){

        FileManager fileManager = new FileManager(workity, "players/" + playerUniqueId);
        playerList.put(playerUniqueId, fileManager);
        listUniqueIds.add(playerUniqueId.toString());

    }

    @Override
    public Database insertJobData(UUID playerUniqueId, String jobName, String playerData, String newData) {
        playerList.get(playerUniqueId).set(playerUniqueId + ".jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public Database insertJobData(UUID playerUniqueId, String jobName, String playerData, int newData) {
        playerList.get(playerUniqueId).set(playerUniqueId + ".jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public Database insertJobData(UUID playerUniqueId, String jobName, String playerData, double newData) {
        playerList.get(playerUniqueId).set(playerUniqueId + ".jobs. " + jobName + "." + playerData, newData);
        return this;
    }

    @Override
    public void insertData(UUID playerUniqueId, String playerData, int newData) {
        playerList.get(playerUniqueId).set(playerUniqueId + ".jobs. " + playerData, newData);
    }


    @Override
    public String getJobStringData(UUID playerUniqueId, String jobName, String playerData) {
        return playerList.get(playerUniqueId).getString(playerUniqueId + ".jobs." + jobName + "." + playerData);
    }

    @Override
    public int getJobIntData(UUID playerUniqueId, String jobName, String playerData) {
        return playerList.get(playerUniqueId).getInt(playerUniqueId + ".jobs." + jobName + "." + playerData, -1);
    }

    @Override
    public double getJobDoubleData(UUID playerUniqueId, String jobName, String playerData) {
        return playerList.get(playerUniqueId).getDouble(playerUniqueId + ".jobs." + jobName + "." + playerData, -1);
    }

    @Override
    public String getStringData(UUID playerUniqueId, String playerData) {
        return playerList.get(playerUniqueId).getString(playerUniqueId + "." + playerData);
    }

    @Override
    public int getIntData(UUID playerUniqueId, String playerData) {
        return playerList.get(playerUniqueId).getInt(playerUniqueId + "." + playerData, -1);
    }

    @Override
    public double getDoubleData(UUID playerUniqueId, String playerData) {
        return playerList.get(playerUniqueId).getDouble(playerUniqueId + "." + playerData, -1);
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
    public void savePlayer(UUID playerUniqueID) {

    }
}
