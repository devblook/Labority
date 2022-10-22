package me.bryang.labority.loader;

import me.bryang.labority.Labority;
import me.bryang.labority.PluginCore;
import me.bryang.labority.api.Loader;
import me.bryang.labority.listener.PlayerBreakListener;
import me.bryang.labority.listener.plugin.JobListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;


public class ListenersLoader implements Loader {

    private final PluginCore pluginCore;

    public ListenersLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {

        registerListeners(
                new PlayerBreakListener(),
                new JobListener(pluginCore));
    }

    public void registerListeners(Listener... listeners){

        PluginManager pluginManager = Bukkit.getPluginManager();
        Labority labority = pluginCore.getPlugin();

        for (Listener listener : listeners){
            pluginManager.registerEvents(listener, labority);
        }

        System.out.println("[Labority] Listeners loaded.");
    }
}
