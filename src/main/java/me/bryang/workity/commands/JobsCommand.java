package me.bryang.workity.commands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.commands.subcommands.*;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import java.util.List;

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
        ReloadSubCommand.class,
        RemoveLevelSubCommand.class,
        SetLevelSubCommand.class,
        SetMultiplierSubCommand.class,
        StatsSubCommand.class})

public class JobsCommand implements CommandClass {

    private final PluginCore pluginCore;
    private final FileManager messagesFile;

    public JobsCommand(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
    }

    @Command(
            names = "")

    public boolean onMainSubCommand(

            @Sender Player sender) {

        List<String> helpCommandList = messagesFile.getStringList("jobs.help.format");

        for (String message : helpCommandList) {
            sender.sendMessage(message);
        }

        if (!sender.hasPermission("jobs.admin")) {
            return true;
        }

        List<String> helpCommandAdmin = messagesFile.getStringList("jobs.help.admin");

        for (String message : helpCommandAdmin) {
            sender.sendMessage(message);
        }
        return true;
    }

    public PluginCore getPluginCore() {
        return pluginCore;
    }


}