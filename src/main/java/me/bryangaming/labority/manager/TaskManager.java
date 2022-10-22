package me.bryangaming.labority.manager;

import me.bryangaming.labority.Labority;
import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.task.PluginLoadingTask;

public class TaskManager {

    private PluginCore pluginCore;

    private PluginLoadingTask pluginLoadingTask;
    
    public TaskManager(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void load(){
        pluginLoadingTask = new PluginLoadingTask();
    }

    public PluginLoadingTask getPluginLoadingTask(){
        return pluginLoadingTask;
    }

}
