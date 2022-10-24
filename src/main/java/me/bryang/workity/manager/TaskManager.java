package me.bryang.workity.manager;

import me.bryang.workity.PluginCore;
import me.bryang.workity.task.PluginLoadingTask;

public class TaskManager {

    private final PluginCore pluginCore;

    private PluginLoadingTask pluginLoadingTask;

    public TaskManager(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void load() {
        pluginLoadingTask = new PluginLoadingTask(pluginCore);

    }

    public PluginLoadingTask getPluginLoadingTask() {
        return pluginLoadingTask;
    }

}
