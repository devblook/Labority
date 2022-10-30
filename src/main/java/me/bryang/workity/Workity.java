package me.bryang.workity;

import org.bukkit.plugin.java.JavaPlugin;

public class Workity extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginCore pluginCore = new PluginCore(this);
        pluginCore.init();

        pluginCore.getManagerLoader().getTaskManager().getPluginLoadingTask().loadTask();
        pluginCore.getManagerLoader().getTaskManager().getMetricsLoadingTask().loadTask();

        getLogger().info(" Created by " + getDescription().getAuthors().get(0));
        getLogger().info(" You are using the version " + getDescription().getVersion() + ".");
        getLogger().info("Click to support: http://discord.devblook.team/");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
