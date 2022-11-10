package me.bryang.workity.listeners;

import me.bryang.workity.PluginCore;
import me.bryang.workity.action.Action;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.events.JobExecuteEvent;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.WorkActionManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorkExecutorListener implements Listener {


    private final DataLoader dataLoader;
    private final WorkActionManager workActionManager;

    public WorkExecutorListener(PluginCore pluginCore) {

        this.workActionManager = pluginCore.getManagerLoader().getWorkActionManager();

        this.dataLoader = pluginCore.getDataLoader();
    }

    @EventHandler
    public void onWork(JobExecuteEvent event) {
        for (String jobName : dataLoader.getJobDataMap().keySet()) {

            Action action = event.getAction();

            if (!dataLoader.getJobDataMap().get(jobName).isActivityType(action.getType())) {
                continue;
            }

            String dataRequired;

            if (action.getEntity() != null) {
                dataRequired = action.getEntity().getType().name().toUpperCase();
            } else {
                dataRequired = action.getItemStack().getType().name().toUpperCase();
            }

            if (dataLoader.getJobDataMap().get(jobName).getBlockJobDataMap().get(dataRequired) == null) {
                continue;
            }

            PlayerJobData playerJobData = dataLoader.getPlayerJob(event.getTarget()).getJob(jobName);

            if (playerJobData == null) {
                return;
            }

            workActionManager.doActions(
                    Bukkit.getPlayer(event.getTarget()), playerJobData.getName(), dataRequired, playerJobData);

        }
    }

}




