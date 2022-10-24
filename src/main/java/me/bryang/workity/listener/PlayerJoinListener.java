package me.bryang.workity.listener;

import me.bryang.workity.PluginCore;
import me.bryang.workity.loader.DataLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final DataLoader dataLoader;

    public PlayerJoinListener(PluginCore pluginCore) {
        dataLoader = pluginCore.getDataLoader();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if (dataLoader.getPlayerJob(event.getPlayer().getUniqueId()) == null) {
            dataLoader.createPlayerJob(event.getPlayer().getUniqueId());
        }

    }
}
