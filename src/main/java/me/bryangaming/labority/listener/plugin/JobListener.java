package me.bryangaming.labority.listener.plugin;

import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.action.Action;
import me.bryangaming.labority.event.JobsEvent;
import me.bryangaming.labority.manager.file.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobListener implements Listener {

    private final FileManager configFile;

    public JobListener(PluginCore pluginCore){
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
    }

    @EventHandler
    public void onWork(JobsEvent event){
        for (String jobs : configFile.getConfigurationSection("jobs").getKeys(false)){

            Action action = event.getAction();
            if (!configFile.getString("jobs." + jobs + ".type").equalsIgnoreCase(action.getType())){
                continue;
            }

            String dataRequired;
            if (action.getEntity() == null){
                dataRequired = action.getEntity().getType().name();
            }else{
                dataRequired = action.getItemStack().getType().name();
            }

            if (configFile.getString("jobs." + jobs + ".items." + dataRequired) == null){
                continue;
            }


        }
    }
}
