package me.bryangaming.labority.loader;

import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.api.Loader;
import me.bryangaming.labority.commands.JobsCommand;
import org.bukkit.Bukkit;


public class CommandsLoader implements Loader {

    private final PluginCore pluginCore;

    public CommandsLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        Bukkit.getPluginCommand("jobs").setExecutor(new JobsCommand(pluginCore));
    }
}
