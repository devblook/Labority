package me.bryang.workity.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

public class MySQLDatabase implements Database {

    private final int address;
    private final int port;

    private final String hostName;
    private final String userName;
    private final String password;

    private final String tableName;

    private Connection connection;
    private ResultSet resultSet;


    public MySQLDatabase(int address, int port, String hostName, String userName, String password, String tableName) {
        this.address = address;
        this.port = port;

        this.hostName = hostName;
        this.userName = userName;

        this.password = password;

        this.tableName = tableName;
    }

    @Override
    public void initDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" +
                    hostName + "?user=" + userName + "&" + "password=" + password);

            resultSet =
                    connection.createStatement().executeQuery("CREATE TABLE " + tableName +
                            "(jobData varchar(255)," +
                            "actionStats int);");
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void createData(UUID playerUniqueId) {

    }

    @Override
    public Database initActivity(UUID playerUniqueId, boolean toSet) {
        return null;
    }


    @Override
    public Database insertJobData(String jobName, String playerData, String newData) {
        return null;
    }

    @Override
    public Database insertJobData(String jobName, String playerData, int newData) {
        return null;
    }

    @Override
    public Database insertJobData(String jobName, String playerData, double newData) {
        return null;
    }

    @Override
    public void insertData(String playerData, int newData) {

    }

    @Override
    public String getJobStringData(String jobName, String playerData) {
        return null;
    }

    @Override
    public int getJobIntData(String jobName, String playerData) {
        return 0;
    }

    @Override
    public double getJobDoubleData(String jobName, String playerData) {
        return 0;
    }

    @Override
    public String getStringData(String playerData) {
        return null;
    }

    @Override
    public int getIntData(String playerData) {
        return 0;
    }

    @Override
    public double getDoubleData(String playerData) {
        return 0;
    }

    @Override
    public List<String> getPlayerList() {
        return null;
    }

    @Override
    public List<String> getPlayerJobs(UUID playerUniqueId) {
        return null;
    }

    @Override
    public void savePlayerAndCloseActivity() {

    }

    @Override
    public void closeActivity() {

    }

}
