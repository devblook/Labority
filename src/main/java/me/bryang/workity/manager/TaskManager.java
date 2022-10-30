package me.bryang.workity.manager;

import me.bryang.workity.PluginCore;
import me.bryang.workity.task.MetricsLoadingTask;
import me.bryang.workity.task.PluginLoadingTask;

public class TaskManager {

    private final PluginCore pluginCore;

    private PluginLoadingTask pluginLoadingTask;
    private MetricsLoadingTask metricsLoadingTask;

    public TaskManager(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void load() {
        pluginLoadingTask = new PluginLoadingTask(pluginCore);
        metricsLoadingTask = new MetricsLoadingTask(pluginCore);

        pluginLoadingTask.loadTask();
        metricsLoadingTask.loadTask();
    }

    public PluginLoadingTask getPluginLoadingTask() {
        return pluginLoadingTask;
    }

    public MetricsLoadingTask getMetricsLoadingTask() {
        return metricsLoadingTask;
    }
}
