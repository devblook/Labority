package me.bryang.workity.loader;

import me.bryang.workity.Workity;
import me.bryang.workity.PluginCore;
import me.bryang.workity.api.Loader;
import me.bryang.workity.listener.*;
import me.bryang.workity.listener.plugin.JobListener;
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
                new PlayerPlaceListener(pluginCore),
                new PlayerBreakListener(pluginCore),
                new PlayerKillEntityListener(pluginCore),
                new PlayerJoinListener(pluginCore),
                new FurnaceExtractListener(pluginCore),
                new JobListener(pluginCore));
    }

    public void registerListeners(Listener... listeners) {

        PluginManager pluginManager = Bukkit.getPluginManager();
        Workity workity = pluginCore.getPlugin();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, workity);
        }

        System.out.println("[Workity] Listeners loaded.");
    }
}
