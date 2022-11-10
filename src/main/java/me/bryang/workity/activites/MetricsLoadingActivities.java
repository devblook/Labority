package me.bryang.workity.activites;

import me.bryang.workity.PluginCore;
import me.bryang.workity.PluginMetrics;
import me.bryang.workity.Workity;
import me.bryang.workity.interfaces.Activities;
import me.bryang.workity.manager.file.FileManager;

public class MetricsLoadingActivities implements Activities {

    private final Workity workity;
    private final FileManager configFile;

    public MetricsLoadingActivities(PluginCore pluginCore) {
        this.workity = pluginCore.getPlugin();
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
    }

    public void loadTask() {
        if (configFile.getBoolean("enabled-metrics")) {
            new PluginMetrics(workity, 16726);
        }
    }
}
