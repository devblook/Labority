package me.bryang.workity.listener;

import me.bryang.workity.PluginCore;
import me.bryang.workity.action.Action;
import me.bryang.workity.action.JobType;
import me.bryang.workity.events.JobsEvent;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerKillEntityListener implements Listener {

    private final FileManager configFile;

    public PlayerKillEntityListener(PluginCore pluginCore) {
        configFile = pluginCore.getFilesLoader().getConfigFile();
    }

    @EventHandler
    public void PlayerKillMob(EntityDamageByEntityEvent event) {

        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }

        if (((Player) event.getDamager()).getGameMode() == GameMode.CREATIVE) {
            if (configFile.getBoolean("config.disable-creative")) {
                return;
            }
        }

        Player player = (Player) event.getDamager();

        Bukkit.getPluginManager().callEvent(
                new JobsEvent(player.getUniqueId(), new Action(JobType.PLAYER_KILL_ENTITY, event.getEntity())));
    }
}
