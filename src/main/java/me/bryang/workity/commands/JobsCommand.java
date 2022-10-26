package me.bryang.workity.commands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.commands.subcommands.*;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;

@Command(

        names = {"jobs", "work", "workity"},
        desc = "Main command.")
@SubCommandClasses({
        AddLevelSubCommand.class,
        BrowseSubCommand.class,
        HelpSubCommand.class,
        InfoSubCommand.class,
        JoinSubCommand.class,
        LeaveAllSubCommand.class,
        LeaveSubCommand.class,
        MainSubCommand.class,
        ReloadSubCommand.class,
        RemoveLevelSubCommand.class,
        SetLevelSubCommand.class,
        SetMultiplierSubCommand.class,
        StatsSubCommand.class})

public class JobsCommand implements CommandClass{

    private final PluginCore pluginCore;

    public JobsCommand(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    public PluginCore getPluginCore(){
        return pluginCore;
    }


}