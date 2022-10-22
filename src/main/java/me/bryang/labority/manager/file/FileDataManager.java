package me.bryang.labority.manager.file;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FileDataManager extends FileManager {


    public FileDataManager(Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public ConfigurationSection getJobData(UUID playerUniqueID){
        return getConfigurationSection("players.jobs." + playerUniqueID);

    }

    public int getPlayerSize(){
        return getConfigurationSection(("players.jobs")).getKeys(false).size();
    }

    public Set<String> getPlayersKeys(){
        return getConfigurationSection("players.job").getKeys(false);
    }

    public Set<String> getJobsKeys(UUID playerUniqueID){
        return getConfigurationSection("players.job." + playerUniqueID + ".list-jobs").getKeys(false);
    }


    public void setJobData(UUID playerUniqueID, String path, String result){
        set("players.jobs." + playerUniqueID + "." + path, result);


    }

    public void setJobData(UUID playerUniqueID, String path, List<String> result){
        set("players.jobs." + playerUniqueID + "." + path, result);


    }

    public void setJobData(UUID playerUniqueID, String path, int result){
        set("players.jobs." + playerUniqueID + "." + path, result);


    }

}
