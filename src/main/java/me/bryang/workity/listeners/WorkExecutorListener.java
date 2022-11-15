package me.bryang.workity.listeners;

import me.bryang.workity.PluginCore;
import me.bryang.workity.action.JobAction;
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

            JobAction jobAction = event.getAction();

            if (!dataLoader.getJobDataMap().get(jobName).isActivityType(jobAction.getType())) {
                continue;
            }

            String dataRequired;

            if (jobAction.getEntity() != null) {
                dataRequired = jobAction.getEntity().getType().name().toUpperCase();
            } else {
                dataRequired = jobAction.getItemStack().getType().name().toUpperCase();
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




