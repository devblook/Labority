package me.bryang.workity.loader;

import me.bryang.workity.PluginCore;
import me.bryang.workity.api.Loader;
import me.bryang.workity.manager.TaskManager;
import me.bryang.workity.manager.VaultHookManager;

public class ManagerLoader implements Loader {

    private final PluginCore pluginCore;

    private TaskManager taskManager;
    private VaultHookManager vaultHookManager;

    public ManagerLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void load() {

        taskManager = new TaskManager(pluginCore);
        taskManager.load();

        vaultHookManager = new VaultHookManager(pluginCore.getPlugin());
        vaultHookManager.load();

        System.out.println("[Workity] Managers loaded.");

    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public VaultHookManager getVaultHookManager() {
        return vaultHookManager;
    }
}
