package me.bryang.workity;

import me.bryang.workity.api.Core;
import me.bryang.workity.api.Loader;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.loader.FilesLoader;
import me.bryang.workity.loader.ListenersLoader;
import me.bryang.workity.loader.ManagerLoader;
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
        filesLoader.load();

        dataLoader = new DataLoader();
        dataLoader.load();

        managerLoader = new ManagerLoader(this);
        managerLoader.load();

        initLoaders(
                new CommandsLoader(this),
                new ListenersLoader(this));

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
