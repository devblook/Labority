package me.bryangaming.labority.loader;

import me.bryangaming.labority.Labority;
import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.api.Loader;
import me.bryangaming.labority.manager.FileManager;

public class FilesLoader implements Loader {

    private final Labority plugin;
    private FileManager configFile;

    public FilesLoader(Labority plugin){
        this.plugin = plugin;
    }
    @Override
    public void load() {
        configFile = new FileManager(plugin, "config.yml");

    }


    public FileManager getConfigFile() {
        return configFile;
    }
}
