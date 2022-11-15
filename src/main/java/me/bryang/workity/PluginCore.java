package me.bryang.workity;

import me.bryang.workity.loader.*;
import me.bryang.workity.loader.command.CommandsLoader;

public class PluginCore implements Core {

    private final Workity plugin;
    private FilesLoader filesLoader;
    private DataLoader dataLoader;
    private ManagerLoader managerLoader;

    public PluginCore(Workity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        filesLoader = new FilesLoader(plugin);
        dataLoader = new DataLoader(plugin);
        managerLoader = new ManagerLoader(this);

        initLoaders(
                filesLoader,
                dataLoader,
                new CommandsLoader(this),
                new ListenersLoader(this),
                managerLoader
        );

    }


    private void initLoaders(Loader... loaders) {
        for (Loader loader : loaders) {
            loader.load();
        }
    }


    public FilesLoader getFilesLoader() {
        return filesLoader;
    }

    public DataLoader getDataLoader() {
        return dataLoader;
    }

    public ManagerLoader getManagerLoader() {
        return managerLoader;
    }

    public Workity getPlugin() {
        return plugin;
    }
}
