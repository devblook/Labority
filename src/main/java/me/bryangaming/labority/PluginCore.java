package me.bryangaming.labority;

import me.bryangaming.labority.api.Core;
import me.bryangaming.labority.api.Loader;
import me.bryangaming.labority.loader.CommandsLoader;
import me.bryangaming.labority.loader.DataLoader;
import me.bryangaming.labority.loader.FilesLoader;
import me.bryangaming.labority.loader.ListenersLoader;

public class PluginCore implements Core{

    private final Labority plugin;

    private FilesLoader filesLoader;
    private DataLoader dataLoader;

    public PluginCore(Labority plugin){
        this.plugin = plugin;
    }

    @Override
    public void init() {

        filesLoader = new FilesLoader(plugin);
        filesLoader.load();

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
    public Labority getPlugin(){
        return plugin;
    }
}
