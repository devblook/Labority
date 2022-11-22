package me.bryang.workity.loader;

import me.bryang.workity.Workity;
import me.bryang.workity.manager.file.FileManager;

public class FilesLoader implements Loader {

    private final Workity plugin;

    private FileManager configFile;
    private FileManager messagesFile;

    public FilesLoader(Workity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void load() {

        configFile = new FileManager(plugin, "config");
        messagesFile = new FileManager(plugin, "messages");

        plugin.getLogger().info("Files loaded");
    }


    public FileManager getConfigFile() {
        return configFile;
    }

    public FileManager getMessagesFile() {
        return messagesFile;
    }

}
