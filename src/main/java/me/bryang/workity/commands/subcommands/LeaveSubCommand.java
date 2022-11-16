package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class LeaveSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final Database database;
    private final DataLoader dataLoader;

    public LeaveSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.database = jobsCommand.getPluginCore().getDatabaseLoader().getDatabase();
        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "leave")

    public boolean onLeaveSubCommand(

            @Sender Player sender,
            @OptArg("") @Text String jobText) {


        if (jobText.isEmpty()) {
            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs leave [job] [job]..."));
            return true;
        }

        String[] jobNames = jobText.toLowerCase().split(" ");

        for (String jobName : jobNames) {

            if (!(configFile.isConfigurationSection("jobs." + jobName))) {

                sender.sendMessage(messagesFile.getString("error.unknown-job")
                        .replace("%job%", jobName));
                continue;

            }

            PlayerData playerDataLeave = dataLoader.getPlayerJob(sender.getUniqueId());

            if (!playerDataLeave.hasTheJob(jobName)) {

                sender.sendMessage(messagesFile.getString("error.already-leave-job")
                        .replace("%job%", configFile.getString("jobs." + jobName + ".name")));
                continue;

            }

            playerDataLeave.removeJob(jobName);

            database
                    .insertJobData(sender.getUniqueId(), jobName, "level", "")
                    .insertJobData(sender.getUniqueId(), jobName, "xp", "")
                    .save();

            sender.sendMessage(messagesFile.getString("jobs.leave.message")
                    .replace("%job%", configFile.getString("jobs." + jobName + ".name")));
        }

        return true;
    }

}
