package me.bryang.workity.manager;

import me.bryang.workity.PluginCore;
import me.bryang.workity.activites.JobsLoadingActivities;
import me.bryang.workity.activites.MetricsLoadingActivities;
import me.bryang.workity.activites.PluginLoadingActivities;
import me.bryang.workity.interfaces.Activities;

public class ActivitiesManager {

    private final PluginCore pluginCore;

    private PluginLoadingActivities pluginLoadingActivities;
    private MetricsLoadingActivities metricsLoadingActivities;
    private JobsLoadingActivities jobsLoadingActivities;

    public ActivitiesManager(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void load() {
        pluginLoadingActivities = new PluginLoadingActivities(pluginCore);
        metricsLoadingActivities = new MetricsLoadingActivities(pluginCore);
        jobsLoadingActivities = new JobsLoadingActivities(pluginCore);

        loadActivities(
                metricsLoadingActivities,
                jobsLoadingActivities,
                pluginLoadingActivities);
    }

    public void loadActivities(Activities... activities) {
        for (Activities activity : activities) {
            activity.loadTask();
        }
    }

    public JobsLoadingActivities getJobsLoadingActivities() {
        return jobsLoadingActivities;
    }

    public PluginLoadingActivities getPluginLoadingTask() {
        return pluginLoadingActivities;
    }

    public MetricsLoadingActivities getMetricsLoadingTask() {
        return metricsLoadingActivities;
    }
}
