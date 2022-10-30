package me.bryang.workity.listener;

import me.bryang.workity.PluginCore;
import me.bryang.workity.action.Action;
import me.bryang.workity.action.JobType;
import me.bryang.workity.events.JobExecuteEvent;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class WorkDeathListener implements Listener {

    private final FileManager configFile;

    public WorkDeathListener(PluginCore pluginCore) {
        configFile = pluginCore.getFilesLoader().getConfigFile();
    }

    @EventHandler
    public void playerKillMob(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) return;
        Player player = (Player) event.getDamager();

        if (player.getGameMode() == GameMode.CREATIVE) {
            if (configFile.getBoolean("config.disable-creative")) return;
        }

        Bukkit.getPluginManager().callEvent(new JobExecuteEvent(
                player.getUniqueId(), new Action(JobType.PLAYER_KILL_ENTITY, event.getEntity())
        ));
    }
}
