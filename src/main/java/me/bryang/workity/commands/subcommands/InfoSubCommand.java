package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.data.jobs.BlockJobData;
import me.bryang.workity.data.jobs.JobData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class InfoSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final DataLoader dataLoader;

    public InfoSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "info")

    public boolean onInfoSubCommand(

            @Sender Player sender,
            @OptArg("") String jobArgument) {

        if (jobArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs info [trabajo]"));
            return true;

        }

        String jobNameInfo = jobArgument.toLowerCase();

        if (configFile.getConfigurationSection("jobs." + jobNameInfo) == null) {

            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", jobNameInfo));
            return true;

        }

        JobData jobData = dataLoader.getJob(jobNameInfo);

        PlayerData playerDataInfo = dataLoader.getPlayerJob(sender.getUniqueId());

        if (playerDataInfo.getJob(jobNameInfo) == null) {
            sender.sendMessage(messagesFile.getString("error.dont-join-job"));

            for (String message : messagesFile.getStringList("jobs.info.message")) {

                if (!message.contains("%job_format%")) {
                    sender.sendMessage(message.replace("%job_name%", jobData.getJobName()));
                    continue;
                }

                for (String item : jobData.getBlockJobDataMap().keySet()) {

                    BlockJobData blockJobData = jobData.getBlockData(item);

                    sender.sendMessage(message
                            .replace("%job_format%", "")
                            .replace("%item_name%", item)
                            .replace("%gain_money%", String.valueOf(blockJobData.getGainMoney()))
                            .replace("%gain_xp%", String.valueOf(blockJobData.getGainXP())));
                }
            }


            return true;
        }

        for (String message : messagesFile.getStringList("jobs.info.message")) {

            if (!message.contains("%job_format%")) {
                sender.sendMessage(message.replace("%job_name%", jobData.getJobName()));
                continue;
            }

            for (String item : jobData.getBlockJobDataMap().keySet()) {

                BlockJobData blockJobData = jobData.getBlockData(item);

                sender.sendMessage(message
                        .replace("%job_format%", "")
                        .replace("%item_name%", item)
                        .replace("%gain_money%", String.valueOf(blockJobData.getGainMoney()))
                        .replace("%gain_xp%", String.valueOf(blockJobData.getGainXP())));
            }
        }


        return true;
    }
}
