package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class LeaveAllSubCommand implements CommandClass {

    private final FileManager messagesFile;

    private final DataLoader dataLoader;
    private final Database database;

    public LeaveAllSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.database = jobsCommand.getPluginCore().getDatabaseLoader().getDatabase();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "leave-all")

    public boolean onLeaveSubCommand(

            @Sender Player sender) {

        PlayerData playerDataLeaveAll = dataLoader.getPlayerJob(sender.getUniqueId());

        if (playerDataLeaveAll.getJobSize() == 0) {
            sender.sendMessage(messagesFile.getString("error.dont-join-any-jobs"));
            return true;
        }

        playerDataLeaveAll.getJobsMap().clear();

        for (String jobName : database.getPlayerJobs(sender.getUniqueId())) {

            database
                    .initActivity(sender.getUniqueId(), true)
                    .insertJobData(jobName, "level", "")
                    .insertJobData(jobName, "xp", "")
                    .savePlayerAndCloseActivity();

        }

        sender.sendMessage(messagesFile.getString("jobs.leave-all.message"));
        return true;
    }

}
