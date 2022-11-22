package me.bryang.workity.loader;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.database.Database;
import me.bryang.workity.database.PlayerFilesDatabase;
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

        if (configFile.getString("database.type").equalsIgnoreCase("NONE")) {
            database = new PlayerFilesDatabase(workity);
        }
    }

    public Database getDatabase() {
        return database;
    }
}
