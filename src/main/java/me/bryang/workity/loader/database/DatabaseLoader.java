package me.bryang.workity.loader.database;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.database.Database;
import me.bryang.workity.database.PlayerFilesDatabase;
import me.bryang.workity.database.PlayerJsonDatabase;
import me.bryang.workity.loader.Loader;
import me.bryang.workity.manager.file.FileManager;

public class DatabaseLoader implements Loader {

    private final Workity workity;

    private final FileManager configFile;
    private Database database;

    public DatabaseLoader(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
    }


    @Override
    public void load() {

        DatabaseType databaseType = DatabaseType.valueOf(configFile.getString("database.type").toUpperCase());

        switch (databaseType){

            case YML:
                database = new PlayerFilesDatabase(workity);
                break;

            case JSON:
                database = new PlayerJsonDatabase(workity);
                break;
            default:
                workity.getLogger().info("Error - Database type not found. Put this: " + databaseType.toString());
        }

        database.initDatabase();
    }

    public Database getDatabase() {
        return database;
    }
}
