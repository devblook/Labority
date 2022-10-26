package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.JobData;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.TextUtils;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RemoveLevelSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;
    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public RemoveLevelSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();
        this.playersFile = jobsCommand.getPluginCore().getFilesLoader().getPlayersFile();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }


    @Command(
            names = "remove-level",
            permission = "jobs.admin")

    public boolean onRemoveLevelSubCommand(

            @Sender Player sender,
            @OptArg("") String targetArgument,
            @OptArg("") String jobArgument,
            @OptArg String levelArgument) {

        if (targetArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [job] [level]"));
            return true;

        }

        Player targetRemoveLevel = Bukkit.getPlayer(targetArgument);

        if (targetRemoveLevel == null) {
            sender.sendMessage(messagesFile.getString("error.no-online"));
            return true;

        }

        if (jobArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [level]"));
            return true;

        }

        PlayerData playerDataRemoveLevel = dataLoader.getPlayerJob(targetRemoveLevel.getUniqueId());

        if (playersFile.isConfigurationSection("jobs." + jobArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", configFile.getString("jobs." + jobArgument + ".name")));
            return true;
        }

        if (levelArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [level]"));
            return true;

        }


        if (!StringUtils.isNumeric(levelArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-number"));
            return true;
        }

        if (Integer.parseInt(levelArgument) < 0) {
            sender.sendMessage(messagesFile.getString("error.negative-number"));
            return true;
        }

        if (playerDataRemoveLevel.getJob(jobArgument) == null) {
            playerDataRemoveLevel.addJob(jobArgument);
        }

        JobData jobDataRemoveLevel = playerDataRemoveLevel.getJob(jobArgument);

        if (jobDataRemoveLevel.getLevel() - Integer.parseInt(levelArgument) < 0) {
            sender.sendMessage(messagesFile.getString("error.minor-0"));
            return true;
        }

        jobDataRemoveLevel.setLevel(jobDataRemoveLevel.getLevel() - Integer.parseInt(levelArgument));
        jobDataRemoveLevel.setMaxXP(
                TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"),
                        jobDataRemoveLevel.getLevel() - Integer.parseInt(levelArgument)));

        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".level", levelArgument);
        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".xp", 0);
        playersFile.save();

        sender.sendMessage(messagesFile.getString("jobs.remove-level.message")
                .replace("%level%", levelArgument)
                .replace("%job%", jobArgument)
                .replace("%player%", targetRemoveLevel.getName()));
        return true;
    }

}
