package me.bryang.workity.database;

import com.google.gson.JsonParser;
import me.bryang.workity.Workity;
import me.bryang.workity.manager.file.FileManager;
import org.json.simple.parser.JSONParser;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.zip.InflaterInputStream;

public class PlayerJsonDatabase implements Database {


    private final Workity workity;

    private final Map<UUID, File> playerList = new HashMap<>();

    private JsonObject jsonObject;
    private JsonObjectBuilder jsonObjectBuilder;

    List<String> listUniqueIds;


    public PlayerJsonDatabase(Workity workity) {
        this.workity = workity;

    }

    @Override
    public void initDatabase() {
        File jobsFolder = new File(workity.getDataFolder().getPath() + "/players" );
        jobsFolder.mkdir();
    }

    @Override
    public void createData(UUID playerUniqueId) {

        File fileManager = new File(workity.getDataFolder().getPath() + " /players/" + playerUniqueId);

        try {
            fileManager.createNewFile();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        playerList.put(playerUniqueId, fileManager);
        listUniqueIds.add(playerUniqueId.toString());

    }

    @Override
    public Database initActivity(UUID playerUniqueId, boolean toSet){
        try {

            InputStream inputStream = new FileInputStream(playerList.get(playerUniqueId));

            jsonObject = Json.createReader(inputStream).readObject();

            if (toSet) {
                jsonObjectBuilder = Json.createObjectBuilder(jsonObject);
            }

        } catch (Exception exception) {

            exception.printStackTrace();

        }
        return this;
    }

    @Override
    public Database insertJobData(String jobName, String playerData, String newData) {

        jsonObjectBuilder.add("jobs", Json.createObjectBuilder())
                    .add(jobName, Json.createObjectBuilder())
                    .add(playerData, newData);



        return this;
    }

    public test(){

    }
    @Override
    public Database insertJobData(String jobName, String playerData, int newData) {


        jsonObjectBuilder.add("jobs", Json.createObjectBuilder())
                .add(jobName, Json.createObjectBuilder())
                .add(playerData, newData);
        return this;
    }

    @Override
    public Database insertJobData(String jobName, String playerData, double newData) {
        jsonObjectBuilder.add("jobs", Json.createObjectBuilder())
                .add(jobName, Json.createObjectBuilder())
                .add(playerData, newData);
        return this;
    }

    @Override
    public void insertData(String playerData, int newData) {
        jsonObjectBuilder.add("jobs", Json.createObjectBuilder())
                .add(playerData, newData);    }


    @Override
    public String getJobStringData(String jobName, String playerData) {
        return jsonObject.getJsonObject("jobs").getJsonObject(jobName).getString(playerData);
    }

    @Override
    public int getJobIntData(String jobName, String playerData) {
        return jsonObject.getJsonObject("jobs").getJsonObject(jobName).getInt(playerData);
    }

    @Override
    public double getJobDoubleData(String jobName, String playerData) {
        return jsonObject.getJsonObject("jobs").getJsonObject(jobName).getInt(playerData);
    }

    @Override
    public String getStringData(String playerData) {
        return jsonObject.getJsonObject("jobs").getString(playerData);

    }

    @Override
    public int getIntData(String playerData) {
        return jsonObject.getJsonObject("jobs").getInt(playerData);
    }

    @Override
    public double getDoubleData(String playerData) {
        return jsonObject.getJsonObject("jobs").getInt(playerData);
    }

    @Override
    public List<String> getPlayerList() {
        return new ArrayList<>(listUniqueIds);
    }

    @Override
    public List<String> getPlayerJobs(UUID playerUniqueID) {
        return new ArrayList<>(jsonObject.getJsonObject("jobs").keySet());
    }

    @Override
    public void savePlayerAndCloseActivity() {
        jsonObjectBuilder.build();

        jsonObject = null;
        jsonObjectBuilder = null;

    }

    @Override
    public void closeActivity(){
        jsonObject = null;
    }
}
