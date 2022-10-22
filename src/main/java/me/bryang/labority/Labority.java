package me.bryang.labority;

import org.bukkit.plugin.java.JavaPlugin;

public class Labority extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginCore pluginCore = new PluginCore(this);
        pluginCore.init();

        pluginCore.getManagerLoader().getTaskManager().getPluginLoadingTask().loadTask();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
