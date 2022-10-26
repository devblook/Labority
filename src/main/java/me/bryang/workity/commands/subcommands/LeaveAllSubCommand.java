package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class LeaveAllSubCommand implements CommandClass {

    private final FileManager messagesFile;
    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public LeaveAllSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.playersFile = jobsCommand.getPluginCore().getFilesLoader().getPlayersFile();

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

        for (String text : playersFile.getJobsKeys(sender.getUniqueId())) {
            playersFile.setJobData(sender.getUniqueId(), "job-list." + text + ".level", "");
            playersFile.setJobData(sender.getUniqueId(), "job-list." + text + ".xp", "");
            playersFile.save();
        }
        sender.sendMessage(messagesFile.getString("jobs.leave-all.message"));
        return true;
    }

}
