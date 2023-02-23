package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.MathLevelsUtils;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RemoveLevelSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final Database database;
    private final DataLoader dataLoader;

    public RemoveLevelSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.database = jobsCommand.getPluginCore().getDatabaseLoader().getDatabase();
        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }


    @Command(
            names = "remove-level",
            permission = "jobs.admin")

    public boolean onRemoveLevelSubCommand(

            @Sender Player sender,
            @OptArg("") String targetArgument,
            @OptArg("") String jobArgument,
            @OptArg("-1") int levelArgument) {

        if (targetArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [job] [level]"));
            return true;

        }

        Player targetRemoveLevel = Bukkit.getPlayerExact(targetArgument);

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

        if (!dataLoader.jobExists(jobArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", jobArgument));
            return true;
        }

        if (levelArgument == -1) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [level]"));
            return true;

        }


        if (levelArgument < 0) {
            sender.sendMessage(messagesFile.getString("error.negative-number"));
            return true;
        }

        if (playerDataRemoveLevel.getJob(jobArgument) == null) {
            playerDataRemoveLevel.addJob(jobArgument);
        }

        PlayerJobData playerJobDataRemoveLevel = playerDataRemoveLevel.getJob(jobArgument);

        if (playerJobDataRemoveLevel.getLevel() - levelArgument < 0) {
            sender.sendMessage(messagesFile.getString("error.minor-0"));
            return true;
        }

        playerJobDataRemoveLevel.setLevel(playerJobDataRemoveLevel.getLevel() - levelArgument);
        playerJobDataRemoveLevel.setMaxXP(
                MathLevelsUtils.calculateNumber(configFile.getString("config.formula.max-xp"),
                        playerJobDataRemoveLevel.getLevel() - levelArgument));


        database
                .initActivity(sender.getUniqueId(), true)
                .insertJobData(jobArgument, "level", playerJobDataRemoveLevel.getLevel())
                .insertJobData(jobArgument, "xp", 0)
                .savePlayerAndCloseActivity();

        sender.sendMessage(messagesFile.getString("jobs.remove-level.message")
                .replace("%level%", String.valueOf(levelArgument))
                .replace("%job%", jobArgument)
                .replace("%player%", targetRemoveLevel.getName()));
        return true;
    }

}
