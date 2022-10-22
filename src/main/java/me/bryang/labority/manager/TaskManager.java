package me.bryang.labority.manager;

import me.bryang.labority.PluginCore;
import me.bryang.labority.task.PluginLoadingTask;

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
