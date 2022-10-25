package me.bryang.workity.commands.subcommands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class BrowseSubCommand implements CommandClass {


    private final FileManager messagesFile;

    public BrowseSubCommand(PluginCore pluginCore){
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
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
