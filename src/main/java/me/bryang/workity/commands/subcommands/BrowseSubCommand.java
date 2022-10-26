package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class BrowseSubCommand implements CommandClass {


    private final FileManager messagesFile;

    public BrowseSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
    }

    @Command(
            names = "browse")

    public boolean onBrowseSubCommand(

            @Sender Player sender) {

        for (String message : messagesFile.getStringList("jobs.browse.list")) {
            sender.sendMessage(message);
        }
        return true;
    }

}
