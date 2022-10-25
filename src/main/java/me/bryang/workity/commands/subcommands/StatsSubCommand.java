package me.bryang.workity.commands.subcommands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.JobData;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class StatsSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final DataLoader dataLoader;

    public StatsSubCommand(PluginCore pluginCore){
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
        this.configFile = pluginCore.getFilesLoader().getConfigFile();

        this.dataLoader = pluginCore.getDataLoader();
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

            if (!message.contains("%job_format%")) {
                sender.sendMessage(message);
                continue;
            }

            for (JobData jobData : playerDataStats.getJobsMap().values()) {

                sender.sendMessage(message
                        .replace("%job_format%", "")
                        .replace("%job_name%", configFile.getString("jobs." + jobData.getName() + ".name"))
                        .replace("%level%", String.valueOf(jobData.getLevel()))
                        .replace("%xp%", String.valueOf(jobData.getXpPoints()))
                        .replace("%max_xp%", String.valueOf(jobData.getMaxXP())));

            }

        }
        return true;
    }

}
