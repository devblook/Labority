package me.bryang.workity.database;

import java.util.List;
import java.util.UUID;

public interface Database {

    void initDatabase();

    void createData(UUID playerUniqueId);
    Database insertJobData(UUID playerUniqueId, String jobName, String playerData, String newData);
    Database insertJobData(UUID playerUniqueId, String jobName, String playerData, int newData);
    Database insertJobData(UUID playerUniqueId, String jobName, String playerData, double newData);

    void insertData(UUID playerUniqueId, String playerData, int newData);

    String getJobStringData(UUID playerUniqueId, String jobName, String playerData);
    int getJobIntData(UUID playerUniqueId, String jobName, String playerData);
    double getJobDoubleData(UUID playerUniqueId, String jobName, String playerData);

    String getStringData(UUID playerUniqueId, String playerData);
    int getIntData(UUID playerUniqueId, String playerData);
    double getDoubleData(UUID playerUniqueId, String playerData);


    List<String> getPlayerList();
    List<String> getPlayerJobs(UUID playerUniqueId);
    void savePlayer(UUID playerUniqueId);



}
