package me.bryang.workity.task;

import me.bryang.workity.PluginCore;
import me.bryang.workity.PluginMetrics;
import me.bryang.workity.Workity;
import me.bryang.workity.manager.file.FileManager;

public class MetricsLoadingTask {

    private final Workity workity;
    private final FileManager configFile;

    public MetricsLoadingTask(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
    }

    public void loadTask() {
        if (configFile.getBoolean("enabled-metrics")) {
            PluginMetrics pluginMetrics = new PluginMetrics(workity, 16726);
        }
    }
}
