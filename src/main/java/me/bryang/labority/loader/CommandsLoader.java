package me.bryang.labority.loader;

import me.bryang.labority.PluginCore;
import me.bryang.labority.api.Loader;
import me.bryang.labority.commands.JobsCommand;
import org.bukkit.Bukkit;


public class CommandsLoader implements Loader {

    private final PluginCore pluginCore;

    public CommandsLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        Bukkit.getPluginCommand("jobs").setExecutor(new JobsCommand(pluginCore));

        System.out.println("[Labority] Commands loaded.");

    }
}
