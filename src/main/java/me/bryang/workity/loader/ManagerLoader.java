package me.bryang.workity.loader;

import me.bryang.workity.PluginCore;
import me.bryang.workity.manager.ActivitiesManager;
import me.bryang.workity.manager.ConvertFileToMapManager;
import me.bryang.workity.manager.VaultHookManager;
import me.bryang.workity.manager.WorkActionManager;

public class ManagerLoader implements Loader {

    private final PluginCore pluginCore;

    private ActivitiesManager activitiesManager;
    private VaultHookManager vaultHookManager;
    private WorkActionManager workActionManager;
    private ConvertFileToMapManager convertFileToMapManager;

    public ManagerLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void load() {
        activitiesManager = new ActivitiesManager(pluginCore);
        activitiesManager.load();

        vaultHookManager = new VaultHookManager(pluginCore.getPlugin());
        vaultHookManager.load();

        workActionManager = new WorkActionManager(pluginCore);
        workActionManager.loadActions();

        pluginCore.getPlugin().getLogger().info(" Managers loaded.");
    }

    public ActivitiesManager getTaskManager() {
        return activitiesManager;
    }

    public VaultHookManager getVaultHookManager() {
        return vaultHookManager;
    }

    public WorkActionManager getWorkActionManager() {
        return workActionManager;
    }

    public ConvertFileToMapManager getConvertFileToMapManager() {
        return convertFileToMapManager;
    }
}
