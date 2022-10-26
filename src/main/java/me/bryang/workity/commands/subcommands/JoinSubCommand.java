package me.bryang.workity.commands.subcommands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.TextUtils;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class JoinSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;
    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public JoinSubCommand(JobsCommand jobsCommand){
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();
        this.playersFile = jobsCommand.getPluginCore().getFilesLoader().getPlayersFile();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
    }

    @Command(
            names = "join")

    public boolean onJoinSubCommand(

            @Sender Player sender,
            @OptArg("") String jobArgument) {

        if (jobArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs join [job]"));
            return true;

        }

        String jobName = jobArgument.toLowerCase();

        if (!configFile.isConfigurationSection("jobs." + jobName)) {

            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", jobName));
            return true;

        }

        PlayerData playerData = dataLoader.getPlayerJob(sender.getUniqueId());

        if (playerData.hasTheJob(jobName)) {

            sender.sendMessage(messagesFile.getString("error.already-have-job")
                    .replace("%job%", configFile.getString("jobs." + jobName + ".name")));
            return true;

        }

        if (playerData.getJobSize() > configFile.getInt("config.limit-jobs")) {
            sender.sendMessage(messagesFile.getString("error.limited-jobs"));
            return true;
        }

        playerData.addJob(jobName);
        playerData.getJob(jobName).setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), 1));

        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobName + ".level", 1);
        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobName + ".xp", 0);
        playersFile.save();

        sender.sendMessage(messagesFile.getString("jobs.join.message")
                .replace("%job%", configFile.getString("jobs." + jobName + ".name")));
        return true;
    }

}
