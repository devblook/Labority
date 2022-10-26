package me.bryang.workity.commands.subcommands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import java.util.List;

public class MainSubCommand implements CommandClass{

    private final FileManager messagesFile;

    public MainSubCommand(JobsCommand jobsCommand){
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
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

}
