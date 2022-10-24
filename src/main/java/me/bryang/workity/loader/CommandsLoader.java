package me.bryang.workity.loader;

import me.bryang.workity.PluginCore;
import me.bryang.workity.api.Loader;
import me.bryang.workity.commands.JobsCommand;
import org.bukkit.Bukkit;


public class CommandsLoader implements Loader {

    private final PluginCore pluginCore;

    public CommandsLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        Bukkit.getPluginCommand("jobs").setExecutor(new JobsCommand(pluginCore));

        System.out.println("[Workity] Commands loaded.");

    }
}
