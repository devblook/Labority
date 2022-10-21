package me.bryangaming.labority.loader;

import me.bryangaming.labority.Labority;
import me.bryangaming.labority.api.Loader;
import me.bryangaming.labority.manager.file.FileDataManager;
import me.bryangaming.labority.manager.file.FileManager;

public class FilesLoader implements Loader {

    private final Labority plugin;

    private FileManager configFile;
    private FileManager messagesFile;

    private FileDataManager playersFile;

    public FilesLoader(Labority plugin){

        this.plugin = plugin;

    }

    @Override
    public void load() {

        configFile = new FileManager(plugin, "config");
        messagesFile = new FileManager(plugin, "messages");

        playersFile = new FileDataManager(plugin, "players");
    }


    public FileManager getConfigFile() {
        return configFile;
    }

    public FileManager getMessagesFile(){
        return messagesFile;
    }

    public FileDataManager getPlayersFile(){
        return playersFile;
    }
}
