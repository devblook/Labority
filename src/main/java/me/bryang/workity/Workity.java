package me.bryang.workity;

import org.bukkit.plugin.java.JavaPlugin;

public class Workity extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginCore pluginCore = new PluginCore(this);
        pluginCore.init();

        pluginCore.getManagerLoader().getTaskManager().getPluginLoadingTask().loadTask();
        pluginCore.getManagerLoader().getTaskManager().getMetricsLoadingTask().loadTask();

        Bukkit.getLogger("[Workity] Created by " + getDescription().getAuthors().get(0));
        Bukkit.getLogger("[Workity] You are using the version " + getDescription().getVersion() + ".");
        Bukkit.getLogger("[Workity] Click to support: http://discord.devblook.team/");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
