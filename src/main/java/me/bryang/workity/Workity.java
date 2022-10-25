package me.bryang.workity;

import org.bukkit.plugin.java.JavaPlugin;

public class Workity extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginCore pluginCore = new PluginCore(this);
        pluginCore.init();

        pluginCore.getManagerLoader().getTaskManager().getPluginLoadingTask().loadTask();
        pluginCore.getManagerLoader().getTaskManager().getMetricsLoadingTask().loadTask();

        System.out.println("[Workity] Created by " + getDescription().getAuthors().get(0));
        System.out.println("[Workity] You are using the version " + getDescription().getVersion() + ".");
        System.out.println("[Workity] @DevBlook");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
