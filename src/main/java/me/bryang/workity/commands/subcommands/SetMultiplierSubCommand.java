package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetMultiplierSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final DataLoader dataLoader;

    public SetMultiplierSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "set-multiplier",
            permission = "jobs.admin")

    public boolean onSetMultiplierSubCommand(

            @Sender Player sender,
            @OptArg("-1") double multiplier) {

        if (!sender.hasPermission("jobs.admin")) {
            sender.sendMessage(messagesFile.getString("error.no-permission"));
            return true;
        }


        if (multiplier == -1) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-multiplier [multiplier]"));
            return true;

        }

        dataLoader.setServerMultiplier(multiplier);
        sender.sendMessage(messagesFile.getString("jobs.multiplier.set")
                .replace("%multiplier%", String.valueOf(multiplier)));

        if (!configFile.getString("config.multiplier.broadcast").equalsIgnoreCase("none")) {
            Bukkit.broadcastMessage(configFile.getString("config.multiplier.broadcast")
                    .replace("%multiplier%", String.valueOf(multiplier)));
        }
        return true;
    }

}
