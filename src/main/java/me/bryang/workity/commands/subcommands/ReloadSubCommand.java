package me.bryang.workity.commands.subcommands;

import me.bryang.workity.activites.JobsLoadingActivities;
import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.job.JobData;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import java.util.Map;

public class ReloadSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final Map<String, JobData> jobDataMap;
    private final JobsLoadingActivities jobsLoadingActivities;


    public ReloadSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.jobDataMap = jobsCommand.getPluginCore().getDataLoader().getJobDataMap();
        this.jobsLoadingActivities = jobsCommand.getPluginCore().getManagerLoader()
                .getTaskManager().getJobsLoadingActivities();
    }

    @Command(
            names = "reload",
            permission = "jobs.admin")

    public boolean onReloadSubCommand(

            @Sender Player sender) {

        configFile.reload();
        messagesFile.reload();

        jobDataMap.clear();
        jobsLoadingActivities.loadTask();

        sender.sendMessage(messagesFile.getString("jobs.reload"));
        return true;
    }

}
