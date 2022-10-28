package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.JobData;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class StatsSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final DataLoader dataLoader;

    public StatsSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "stats")

    public boolean onStatsSubCommand(

            @Sender Player sender) {

        PlayerData playerDataStats = dataLoader.getPlayerJob(sender.getUniqueId());

        if (playerDataStats.getJobsMap().values().isEmpty()) {
            sender.sendMessage(messagesFile.getString("error.dont-join-any-jobs"));
            return true;
        }

        for (String message : messagesFile.getStringList("jobs.stats.message")) {


            if (!message.contains("%job_format%") && !message.contains("%action-format%")) {
                sender.sendMessage(message);
                continue;
            }

            if (message.contains("%job_format")) {
                for (JobData jobData : playerDataStats.getJobsMap().values()) {

                    sender.sendMessage(message
                            .replace("%job_format%", "")
                            .replace("%job_name%", configFile.getString("jobs." + jobData.getName() + ".name"))
                            .replace("%level%", String.valueOf(jobData.getLevel()))
                            .replace("%xp%", String.valueOf(jobData.getXpPoints()))
                            .replace("%max_xp%", String.valueOf(jobData.getMaxXP())));

                }
            }

            if (message.contains("%action_format")) {
                for (String jobName : playerDataStats.getJobsMap().keySet()) {

                    if (configFile.getBoolean("config." + jobName + ".global-stats")) {
                        sender.sendMessage(message
                                .replace("%action-name%", messagesFile.getString("jobs.stats.global." + jobName))
                                .replace("%action_value%", String.valueOf(playerDataStats.getJob(jobName).getGlobalStats())));
                        continue;
                    }

                    for (String itemName : playerDataStats.getJob(jobName).getJobData().keySet()) {

                        if (!configFile.getBoolean("config." + jobName + ".items." + itemName + ".enabled-stats")) {
                            continue;
                        }

                        sender.sendMessage(message

                                .replace("%action-name%",
                                        messagesFile.getString("jobs.stats.item." + jobName)
                                                .replace("%data%", itemName))

                                .replace("%action_value%",
                                        String.valueOf(playerDataStats.getJob(jobName).getGlobalStats())));


                    }
                }
            }
        }
        return true;
    }

}


