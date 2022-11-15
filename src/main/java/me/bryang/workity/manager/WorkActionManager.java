package me.bryang.workity.manager;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.manager.jobs.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkActionManager {

    private final PluginCore pluginCore;

    private final List<JobManager> jobManagers = new ArrayList<>();

    public WorkActionManager(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void loadActions() {
        addActions(
                new JobSetMultiplierManager(pluginCore),
                new JobGainRewardManager(pluginCore),
                new JobIncreaseLevelManager(pluginCore),
                new JobAddStatsPointsManager(pluginCore),
                new JobActionRewardsManager(pluginCore));

    }

    public void addActions(JobManager... newJobManagers) {
        jobManagers.addAll(Arrays.asList(newJobManagers));
    }

    public void doActions(Player player, String jobName, String itemName, PlayerJobData playerJobData) {
        for (JobManager jobManager : jobManagers) {
            jobManager.doWorkAction(player, jobName, itemName, playerJobData);
        }
    }
}
