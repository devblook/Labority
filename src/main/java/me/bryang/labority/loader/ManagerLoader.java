package me.bryang.labority.loader;

import me.bryang.labority.PluginCore;
import me.bryang.labority.api.Loader;
import me.bryang.labority.manager.TaskManager;
import me.bryang.labority.manager.VaultHookManager;

public class ManagerLoader implements Loader {

    private final PluginCore pluginCore;

    private TaskManager taskManager;
    private VaultHookManager vaultHookManager;

    public ManagerLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    public void load(){

        taskManager = new TaskManager(pluginCore);
        taskManager.load();

        vaultHookManager = new VaultHookManager(pluginCore.getPlugin());
        vaultHookManager.load();

        System.out.println("[Labority] Managers loaded.");

    }

    public TaskManager getTaskManager(){
        return taskManager;
    }

    public VaultHookManager getVaultHookManager(){
        return vaultHookManager;
    }
}
