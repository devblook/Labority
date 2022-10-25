package me.bryang.workity.commands.subcommands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class LeaveSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;
    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public LeaveSubCommand(PluginCore pluginCore){
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();

        this.dataLoader = pluginCore.getDataLoader();
    }

    @Command(
            names = "leave")

    public boolean onLeaveSubCommand(

            @Sender Player sender,
            @OptArg("") String jobArgument) {

        if (jobArgument.isEmpty()) {
            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs leave [trabajo]"));
            return true;
        }

        String jobNameLeave = jobArgument.toLowerCase();

        if (!(configFile.isConfigurationSection("jobs." + jobNameLeave))) {

            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", jobNameLeave));
            return true;

        }

        PlayerData playerDataLeave = dataLoader.getPlayerJob(sender.getUniqueId());

        if (!playerDataLeave.hasTheJob(jobNameLeave)) {

            sender.sendMessage(messagesFile.getString("error.already-leave-job")
                    .replace("%job%", configFile.getString("jobs." + jobNameLeave + ".name")));
            return true;

        }

        playerDataLeave.removeJob(jobNameLeave);

        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameLeave + ".level", "");
        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameLeave + ".xp", "");
        playersFile.save();

        sender.sendMessage(messagesFile.getString("jobs.leave.message")
                .replace("%job%", configFile.getString("jobs." + jobNameLeave + ".name")));
        return true;
    }

}
