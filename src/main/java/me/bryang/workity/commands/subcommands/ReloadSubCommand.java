package me.bryang.workity.commands.subcommands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class ReloadSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;


    public ReloadSubCommand(JobsCommand jobsCommand){
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

    }

    @Command(
            names = "reload",
            permission = "jobs.admin")

    public boolean onReloadSubCommand(

            @Sender Player sender) {

        configFile.reload();
        messagesFile.reload();
        sender.sendMessage(messagesFile.getString("jobs.reload"));
        return true;
    }

}
