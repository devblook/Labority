package me.bryang.labority;

import me.bryang.labority.api.Core;
import me.bryang.labority.api.Loader;
import me.bryang.labority.loader.*;
import me.bryangaming.labority.loader.*;

public class PluginCore implements Core{

    private final Labority plugin;

    private FilesLoader filesLoader;
    private DataLoader dataLoader;
    private ManagerLoader managerLoader;

    public PluginCore(Labority plugin){
        this.plugin = plugin;
    }

    @Override
    public void init() {

        dataLoader = new DataLoader();
        dataLoader.load();

        filesLoader = new FilesLoader(plugin);
        filesLoader.load();

        managerLoader = new ManagerLoader(this);
        managerLoader.load();

        initLoaders(
                new CommandsLoader(this),
                new ListenersLoader(this));



    }


    private void initLoaders(Loader... loaders){
        for (Loader loader : loaders){
            loader.load();
        }
    }


    public FilesLoader getFilesLoader() {
        return filesLoader;
    }

    public DataLoader getDataLoader(){
        return dataLoader;
    }

    public ManagerLoader getManagerLoader(){
        return managerLoader;
    }

    public Labority getPlugin(){
        return plugin;
    }
}
