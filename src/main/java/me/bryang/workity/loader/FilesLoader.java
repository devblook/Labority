package me.bryang.workity.loader;

import me.bryang.workity.Labority;
import me.bryang.workity.api.Loader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;

public class FilesLoader implements Loader {

    private final Labority plugin;

    private FileManager configFile;
    private FileManager messagesFile;

    private FileDataManager playersFile;

    public FilesLoader(Labority plugin) {

        this.plugin = plugin;
    }

    @Override
    public void load() {

        configFile = new FileManager(plugin, "config");
        messagesFile = new FileManager(plugin, "messages");

        playersFile = new FileDataManager(plugin, "players");

        System.out.println("[Labority] Files loaded");
    }


    public FileManager getConfigFile() {
        return configFile;
    }

    public FileManager getMessagesFile() {
        return messagesFile;
    }

    public FileDataManager getPlayersFile() {
        return playersFile;
    }
}
