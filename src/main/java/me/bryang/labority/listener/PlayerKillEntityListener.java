package me.bryang.labority.listener;

import me.bryang.labority.action.Action;
import me.bryang.labority.action.JobType;
import me.bryang.labority.event.JobsEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerKillEntityListener implements Listener {

    @EventHandler
    public void PlayerKillMob(EntityDamageByEntityEvent event){

        if (event.getDamager().getType() != EntityType.PLAYER){
            return;
        }

        Player player = (Player) event.getDamager();

        Bukkit.getPluginManager().callEvent(new JobsEvent(player.getUniqueId(), new Action(JobType.PLAYER_KILL_ENTITY, event.getEntity())));
    }
}
