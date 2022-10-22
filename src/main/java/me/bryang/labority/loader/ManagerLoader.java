package me.bryang.labority.loader;

import me.bryang.labority.PluginCore;
import me.bryang.labority.api.Loader;
import me.bryang.labority.manager.TaskManager;

public class ManagerLoader implements Loader {

    private final PluginCore pluginCore;

    private TaskManager taskManager;

    public ManagerLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    public void load(){

        taskManager = new TaskManager(pluginCore);
        taskManager.load();

    }

    public TaskManager getTaskManager(){
        return taskManager;
    }
}
