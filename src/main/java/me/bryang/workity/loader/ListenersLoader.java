package me.bryang.workity.loader;

import me.bryang.workity.PluginCore;
import me.bryang.workity.Workity;
import me.bryang.workity.interfaces.Loader;
import me.bryang.workity.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class ListenersLoader implements Loader {

    private final PluginCore pluginCore;

    public ListenersLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        registerListeners(
                new WorkPlaceListener(pluginCore),
                new WorkBreakListener(pluginCore),
                new WorkDeathListener(pluginCore),
                new PlayerRegistryListener(pluginCore),
                new WorkFurnaceListener(pluginCore),
                new WorkExecutorListener(pluginCore));
    }

    public void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Workity workity = pluginCore.getPlugin();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, workity);
        }

        workity.getLogger().info("Listeners loaded.");
    }
}
