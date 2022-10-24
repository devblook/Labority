package me.bryang.labority.listener;

import me.bryang.labority.PluginCore;
import me.bryang.labority.action.Action;
import me.bryang.labority.action.JobType;
import me.bryang.labority.events.JobsEvent;
import me.bryang.labority.manager.file.FileManager;
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

        if (((Player) event.getDamager()).getGameMode() == GameMode.ADVENTURE) {
            if (configFile.getBoolean("config.disable-creative")) {
                return;
            }
        }

        Player player = (Player) event.getDamager();

        Bukkit.getPluginManager().callEvent(new JobsEvent(player.getUniqueId(), new Action(JobType.PLAYER_KILL_ENTITY, event.getEntity())));
    }
}
