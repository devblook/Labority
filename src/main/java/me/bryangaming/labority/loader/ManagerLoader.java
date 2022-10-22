package me.bryangaming.labority.loader;

import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.api.Loader;
import me.bryangaming.labority.manager.TaskManager;

public class ManagerLoader implements Loader{

    private PluginCore pluginCore;

    private TaskManager taskManager;

    public ManagerLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    public void load(){
        taskManager = new TaskManager(pluginCore);
    }

    public TaskManager getTaskManager(){
        return taskManager;
    }
}
