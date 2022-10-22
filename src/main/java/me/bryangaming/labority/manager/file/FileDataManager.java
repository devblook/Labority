package me.bryangaming.labority.manager.file;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class FileDataManager extends FileManager {


    public FileDataManager(Plugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public ConfigurationSection getJobData(String playerName){
        return getConfigurationSection("players.jobs." + playerName);

    }


    public void setJobData(String playerName, String path, String result){
        set("players.jobs." + playerName + "." + path, result);


    }

    public void setJobData(String playerName, String path, List<String> result){
        set("players.jobs." + playerName + "." + path, result);


    }

    public void setJobData(String playerName, String path, int result){
        set("players.jobs." + playerName + "." + path, result);


    }

}
