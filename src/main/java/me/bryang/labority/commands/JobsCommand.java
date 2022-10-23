package me.bryang.labority.commands;

import me.bryang.labority.PluginCore;
import me.bryang.labority.data.JobData;
import me.bryang.labority.data.PlayerData;
import me.bryang.labority.loader.DataLoader;
import me.bryang.labority.manager.file.FileDataManager;
import me.bryang.labority.manager.file.FileManager;
import me.bryang.labority.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JobsCommand implements CommandExecutor {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public JobsCommand(PluginCore pluginCore) {

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();

        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();

        this.dataLoader = pluginCore.getDataLoader();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String idk, String[] arguments) {

        if (!(commandSender instanceof Player)) {

            System.out.println(messagesFile.getString("error.console"));
            return true;

        }

        Player sender = (Player) commandSender;

        if (arguments.length < 1) {

            List<String> helpCommandList = messagesFile.getStringList("jobs.help");

            for (String message : helpCommandList) {
                sender.sendMessage(message);
            }

            return true;

        }

        switch (arguments[0]) {

            case "help":
                List<String> helpCommandList = messagesFile.getStringList("jobs.help.format");

                for (String message : helpCommandList) {
                    sender.sendMessage(message);
                }

                if (sender.hasPermission("jobs.admin")){
                    break;
                }

                List<String> helpCommandAdmin = messagesFile.getStringList("jobs.help.admin");

                for (String message : helpCommandAdmin) {
                    sender.sendMessage(message);
                }
                break;

            case "reload":

                if (sender.hasPermission("jobs.admin")){
                    sender.sendMessage(messagesFile.getString("error.no-permission"));
                    break;
                }

                configFile.reload();
                messagesFile.reload();
                sender.sendMessage(messagesFile.getString("jobs.reload"));

                break;

            case "join":


                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs join [job]"));
                    return true;

                }
                String jobName = arguments[1].toLowerCase();

                if (!configFile.isConfigurationSection("jobs." + jobName)) {

                    sender.sendMessage(messagesFile.getString("error.unknown-job")
                            .replace("%job%", configFile.getString("jobs." + jobName + ".name")));
                    return true;

                }

                PlayerData playerData = dataLoader.getPlayerJob(sender.getUniqueId());

                if (playerData.hasTheJob(jobName)) {

                    sender.sendMessage(messagesFile.getString("error.already-have-job")
                            .replace("%job%", configFile.getString("jobs." + jobName + ".name")));
                    return true;

                }

                if (playerData.getJobSize() > configFile.getInt("config.limit-jobs")){
                    sender.sendMessage(messagesFile.getString("error.limited-jobs"));
                }
                playerData.addJob(jobName);
                playerData.getJob(jobName).setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), 1));

                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobName + ".level", 1);
                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobName + ".xp", 0);
                playersFile.save();

                sender.sendMessage(messagesFile.getString("jobs.join.message")
                        .replace("%job%", configFile.getString("jobs." + jobName + ".name")));
                break;

            case "leave":

                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs join [trabajo]"));
                    return true;

                }

                String jobNameLeave = arguments[1].toLowerCase();

                if (!(configFile.isConfigurationSection("jobs." + jobNameLeave))) {

                    sender.sendMessage(messagesFile.getString("error.unknown-job")
                            .replace("%job%", configFile.getString("jobs." + jobNameLeave + ".name")));
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
                break;

            case "browse":

                for (String message : messagesFile.getStringList("jobs.browse.list")) {
                    sender.sendMessage(message);
                }

                break;

            case "stats":
                PlayerData playerDataStats = dataLoader.getPlayerJob(sender.getUniqueId());

                if (playerDataStats.getJobsData().values().isEmpty()){
                    sender.sendMessage(messagesFile.getString("error.dont-join-any-jobs"));
                    break;
                }

                for (String message : messagesFile.getStringList("jobs.stats.message")) {

                    if (!message.contains("%job_format%")) {
                        sender.sendMessage(message);
                        continue;
                    }

                    for (JobData jobData : playerDataStats.getJobsData().values()) {

                        sender.sendMessage(message
                                .replace("%job_format%", "")
                                .replace("%job_name%", configFile.getString("jobs." + jobData.getName() + ".name"))
                                .replace("%level%", String.valueOf(jobData.getLevel()))
                                .replace("%xp%", String.valueOf(jobData.getXpPoints()))
                                .replace("%max_xp%", String.valueOf(jobData.getMaxXP())));

                    }

                }
                break;

            case "info":

                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs info [trabajo]"));
                    return true;

                }

                String jobNameInfo = arguments[1].toLowerCase();

                if (configFile.getConfigurationSection("jobs." + jobNameInfo) == null) {

                    sender.sendMessage(messagesFile.getString("error.unknown-job")
                            .replace("%job%", configFile.getString("jobs." + jobNameInfo + ".name")));
                    return true;

                }

                PlayerData playerDataInfo = dataLoader.getPlayerJob(sender.getUniqueId());

                if (playerDataInfo.getJob(jobNameInfo) == null){
                    sender.sendMessage(messagesFile.getString("error.dont-join-job"));

                    for (String message : messagesFile.getStringList("jobs.info.message")) {

                        if (!message.contains("%job_format%")) {
                            sender.sendMessage(message.replace("%job_name%", configFile.getString("jobs." + jobNameInfo + ".name")));
                            continue;
                        }

                        for (String item : configFile.getConfigurationSection("jobs." + jobNameInfo + ".items").getKeys(false)) {

                            sender.sendMessage(message
                                    .replace("%job_format%", "")
                                    .replace("%item_name%", item)
                                    .replace("%gain_money%", String.valueOf(configFile.getInt("jobs." + jobNameInfo + "items." + item + ".money")))
                                    .replace("%gain_xp%", String.valueOf(configFile.getInt("jobs." + jobNameInfo + "items." + item + ".xp"))));
                        }
                    }


                    break;
                }
                for (String message : messagesFile.getStringList("jobs.info.message")) {

                    if (!message.contains("%job_format%")) {
                        sender.sendMessage(message.replace("%job_name%", configFile.getString("jobs." + jobNameInfo + ".name")));
                        continue;
                    }

                    for (String item : configFile.getStringList("jobs." + jobNameInfo + ".items")) {

                        String[] valueItem = item.split(",");

                        sender.sendMessage(message
                                .replace("%job_format%", "")
                                .replace("%item_name%", String.valueOf(valueItem[0]))
                                .replace("%gain_money%", String.valueOf(valueItem[1]))
                                .replace("%gain_xp%", String.valueOf(valueItem[2])));
                        }
                }

                break;

            case "set-level":
                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs set-level [player] [job] [level]"));
                    return true;

                }

                Player target = Bukkit.getPlayer(arguments[1]);

                if (target == null) {
                    sender.sendMessage(messagesFile.getString("error.no-online"));
                    return true;

                }

                if (arguments.length < 3) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs set-level [player] [level]"));
                    return true;

                }

                PlayerData playerDataSet = dataLoader.getPlayerJob(target.getUniqueId());

                String jobNameSet = arguments[2];

                if (playersFile.isConfigurationSection("jobs." + jobNameSet)){
                    sender.sendMessage(messagesFile.getString("error.unknown-job")
                            .replace("%job%", configFile.getString("jobs." + jobNameSet + ".name")));
                    return true;
                }

                String level = arguments[3];

                if (playerDataSet.getJob(jobNameSet) == null) {
                    playerDataSet.addJob(jobNameSet);
                }

                JobData jobData = playerDataSet.getJob(jobNameSet);

                jobData.setLevel(Integer.parseInt(level));
                jobData.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), Integer.parseInt(level)));

                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameSet + ".level", level);
                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameSet + ".xp", 0);
                playersFile.save();

                sender.sendMessage(messagesFile.getString("jobs.set.message")
                        .replace("%level%", level)
                        .replace("%job%", jobNameSet)
                        .replace("%player%", target.getName()));
            default:
                sender.sendMessage(messagesFile.getString("error.unknown-argument"));
        }
        return true;
    }
}
