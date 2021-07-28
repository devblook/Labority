package me.bryangaming.labority;

import me.bryangaming.labority.api.Core;
import me.bryangaming.labority.api.Loader;
import me.bryangaming.labority.loader.CommandsLoader;
import me.bryangaming.labority.loader.FilesLoader;
import me.bryangaming.labority.loader.ListenersLoader;

public class PluginCore implements Core{

    private Labority plugin;

    private FilesLoader filesLoader;

    public PluginCore(Labority plugin){
        this.plugin = plugin;
    }

    @Override
    public void init() {
        filesLoader = new FilesLoader(plugin);
        filesLoader.load();

        initLoaders(
                new CommandsLoader(),
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

    public Labority getPlugin(){
        return plugin;
    }
}
