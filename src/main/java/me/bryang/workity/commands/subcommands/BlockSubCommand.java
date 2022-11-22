package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

import java.util.Set;

public class BlockSubCommand implements CommandClass {

    private final FileManager messagesFile;

    private final DataLoader dataLoader;

    private final Set<String> jobStatusSet;

    public BlockSubCommand(JobsCommand jobsCommand) {
        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();

        this.jobStatusSet = jobsCommand.getPluginCore().getDataLoader().getJobStatusSet();
    }
    @Command(
            names = "block",
            permission = "jobs.admin")

    public boolean onBlockSubCommand(

            @Sender Player sender,
            @OptArg("") String jobName) {


        if (jobName.isEmpty()){
            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs block [-all/<job>]"));
            return true;
        }

        if (jobName.equalsIgnoreCase("-all")){
            jobStatusSet.addAll(dataLoader.getJobDataMap().keySet());
            sender.sendMessage(messagesFile.getString("jobs.block.all"));
            return true;
        }

        if (!dataLoader.jobExists(jobName)){
            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", jobName));

            return true;
        }

        jobStatusSet.add(jobName);
        sender.sendMessage(messagesFile.getString("jobs.block.job")
                .replace("%job%", jobName));
        return true;

    }
}
