package me.bryang.workity.database;

import java.util.List;
import java.util.UUID;

public interface Database {

    void initDatabase();

    void createData(UUID playerUniqueId);

    Database initActivity(UUID playerUniqueId, boolean toSet);

    Database insertJobData(String jobName, String playerData, String newData);

    Database insertJobData(String jobName, String playerData, int newData);

    Database insertJobData(String jobName, String playerData, double newData);

    void insertData(String playerData, int newData);

    String getJobStringData(String jobName, String playerData);

    int getJobIntData(String jobName, String playerData);

    double getJobDoubleData(String jobName, String playerData);

    String getStringData(String playerData);

    int getIntData(String playerData);

    double getDoubleData(String playerData);


    List<String> getPlayerList();

    List<String> getPlayerJobs(UUID playerUniqueId);

    void savePlayerAndCloseActivity();
    void closeActivity();

}
