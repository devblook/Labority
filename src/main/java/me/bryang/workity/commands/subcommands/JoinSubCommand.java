package me.bryang.workity.commands.subcommands;

import me.bryang.workity.commands.JobsCommand;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.VaultHookManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.MathLevelsUtils;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

public class JoinSubCommand implements CommandClass {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final Database database;
    private final DataLoader dataLoader;

    private final VaultHookManager vaultHookManager;

    public JoinSubCommand(JobsCommand jobsCommand) {
        this.messagesFile = jobsCommand.getPluginCore().getFilesLoader().getMessagesFile();
        this.configFile = jobsCommand.getPluginCore().getFilesLoader().getConfigFile();

        this.database = jobsCommand.getPluginCore().getDatabaseLoader().getDatabase();

        this.dataLoader = jobsCommand.getPluginCore().getDataLoader();
        this.vaultHookManager = jobsCommand.getPluginCore().getManagerLoader().getVaultHookManager();

    }

    @Command(
            names = "join")

    public boolean onJoinSubCommand(

            @Sender Player sender,
            @OptArg("") @Text String jobText) {

        if (jobText.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs join [job] [job]..."));
            return true;

        }

        String[] jobNames = jobText.toLowerCase().split(" ");

        for (String jobName : jobNames) {

            if (!dataLoader.jobExists(jobName)) {

                sender.sendMessage(messagesFile.getString("error.unknown-job")
                        .replace("%job%", jobName));
                continue;

            }

            if (dataLoader.getJobStatusSet().contains(jobName)) {
                sender.sendMessage(messagesFile.getString("error.blocked-job")
                        .replace("%job%", jobName));
                continue;
            }

            PlayerData playerData = dataLoader.getPlayerJob(sender.getUniqueId());


            if (playerData.hasTheJob(jobName)) {

                sender.sendMessage(messagesFile.getString("error.already-have-job")
                        .replace("%job%", dataLoader.getJob(jobName).getJobName()));
                continue;

            }

            if (!sender.hasPermission("config.limit-jobs.bypass-permission")) {

                if (!vaultHookManager.getPermission().hasGroupSupport()) {
                    if (playerData.getJobSize() > configFile.getInt("config.limit-jobs.groups.default")) {
                        sender.sendMessage(messagesFile.getString("error.limited-jobs"));
                        continue;
                    }
                } else {

                    String mainGroup = vaultHookManager.getPermission().getPrimaryGroup(sender);
                    if (configFile.getInt("config.limit-jobs.groups." + mainGroup, -1) == -1) {

                        if (playerData.getJobSize() >
                                configFile.getInt("config.limit-jobs.groups." + mainGroup)) {

                            sender.sendMessage(messagesFile.getString("error.limited-jobs"));
                            continue;
                        }
                    } else {

                        if (playerData.getJobSize() > configFile.getInt("config.limit-jobs.groups.")) {
                            sender.sendMessage(messagesFile.getString("error.limited-jobs"));
                            continue;
                        }
                    }
                }

            }

            playerData.addJob(jobName);
            playerData.getJob(jobName).setMaxXP(
                    MathLevelsUtils.calculateNumber(configFile.getString("config.formula.max-xp"), 1));

            database
                    .initActivity(sender.getUniqueId(), true)
                    .insertJobData(jobName, "level", 1)
                    .insertJobData(jobName, "xp", 0)
                    .savePlayerAndCloseActivity();


            sender.sendMessage(messagesFile.getString("jobs.join.message")
                    .replace("%job%", dataLoader.getJobDataMap().get(jobName).getJobName()));

        }
        return true;
    }

}
