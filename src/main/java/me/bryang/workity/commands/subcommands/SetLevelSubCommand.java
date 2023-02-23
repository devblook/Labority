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

public class SetLevelSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final Database database;
    private final DataLoader dataLoader;

    public SetLevelSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.database = jobsCommand.getPluginCore().getDatabaseLoader().getDatabase();
        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "set-level",
            permission = "jobs.admin")

    public boolean onSetLevelSubCommand(

            @Sender Player sender,
            @OptArg("") String targetArgument,
            @OptArg("") String jobArgument,
            @OptArg("-1") int levelArgument) {

        if (targetArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-level [player] [job] [level]"));
            return true;

        }

        Player target = Bukkit.getPlayerExact(targetArgument);

        if (target == null) {
            sender.sendMessage(messagesFile.getString("error.no-online"));
            return true;

        }

        if (jobArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-level [player] [level]"));
            return true;

        }

        PlayerData playerData = dataLoader.getPlayerJob(target.getUniqueId());

        if (!dataLoader.jobExists(jobArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", configFile.getString("jobs." + jobArgument + ".name")));
            return true;
        }

        if (levelArgument == -1) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-level [player] [level]"));
            return true;

        }


        if (levelArgument < 0) {
            sender.sendMessage(messagesFile.getString("error.negative-number"));
            return true;
        }

        if (playerData.getJob(jobArgument) == null) {
            playerData.addJob(jobArgument);
        }

        PlayerJobData playerJobData = playerData.getJob(jobArgument);

        playerJobData.setLevel(levelArgument);
        playerJobData.setMaxXP(
                MathLevelsUtils.calculateNumber(configFile.getString("config.formula.max-xp"), levelArgument));


        database
                .initActivity(sender.getUniqueId(), true)
                .insertJobData(jobArgument, "level", playerJobData.getLevel())
                .insertJobData(jobArgument, "xp", 0)
                .savePlayerAndCloseActivity();

        sender.sendMessage(messagesFile.getString("jobs.set-level.message")
                .replace("%level%", String.valueOf(levelArgument))
                .replace("%job%", jobArgument)
                .replace("%player%", target.getName()));
        return true;
    }

}
