package me.bryangaming.labority.listener;

import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.loader.DataLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener{

    private final DataLoader dataLoader;

    public PlayerJoinListener(PluginCore pluginCore){
        dataLoader = pluginCore.getDataLoader();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        if (dataLoader.getPlayerJob(event.getPlayer().getUniqueId()) == null){
            dataLoader.createPlayerJob(event.getPlayer().getUniqueId());
        }

    }
}
