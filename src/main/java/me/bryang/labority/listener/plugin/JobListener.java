package me.bryang.labority.listener.plugin;

import me.bryang.labority.PluginCore;
import me.bryang.labority.action.Action;
import me.bryang.labority.data.JobData;
import me.bryang.labority.data.PlayerData;
import me.bryang.labority.events.JobsEvent;
import me.bryang.labority.loader.DataLoader;
import me.bryang.labority.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobListener implements Listener {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final DataLoader dataLoader;

    public JobListener(PluginCore pluginCore){

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();

        this.dataLoader = pluginCore.getDataLoader();
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

            JobData jobData = dataLoader.getPlayerJob(event.getTarget()).getJob(jobs);

            if (jobData == null){
                return;
            }

            String[] jobValue = configFile.getString("jobs." + jobs + ".items." + dataRequired).split(",");

            jobData.setXPPoints(Integer.parseInt(jobValue[1]));

            if (jobData.getMaxXP() <= jobData.getXpPoints()) {
                jobData.setLevel(jobData.getLevel() + 1);
                jobData.setXPPoints(jobData.getMaxXP() - jobData.getXpPoints());

                Player player = Bukkit.getPlayer(event.getTarget());

                player.sendMessage(messagesFile.getString("jobs.gain.level")
                        .replace("%new_level", String.valueOf(jobData.getLevel())));
            }


        }
    }
}
