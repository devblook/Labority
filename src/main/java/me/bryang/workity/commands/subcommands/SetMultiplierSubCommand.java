package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetMultiplierSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;
    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public SetMultiplierSubCommand(JobsCommand jobsCommand){
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();
        this.playersFile = jobsCommand.getPluginCore().getFilesLoader().getPlayersFile();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "set-multiplier",
            permission = "jobs.admin")

    public boolean onSetMultiplierSubCommand(

            @Sender Player sender,
            @OptArg("") String multiplier) {

        if (!sender.hasPermission("jobs.admin")) {
            sender.sendMessage(messagesFile.getString("error.no-permission"));
            return true;
        }


        if (multiplier.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-multiplier [multiplier]"));
            return true;

        }

        if (!StringUtils.isNumeric(multiplier)) {
            sender.sendMessage(messagesFile.getString("error.unknown-number"));
            return true;
        }

        dataLoader.setServerMultiplier(Double.parseDouble(multiplier));
        sender.sendMessage(messagesFile.getString("jobs.multiplier.set")
                .replace("%multiplier%", multiplier));

        if (!configFile.getString("config.multiplier.broadcast").equalsIgnoreCase("none")) {
            Bukkit.broadcastMessage(configFile.getString("config.multiplier.broadcast")
                    .replace("%multiplier%", multiplier));
        }
        return true;
    }

}
