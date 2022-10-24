package me.bryang.workity.commands;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.JobData;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.TextUtils;
import org.apache.commons.lang.StringUtils;
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

            List<String> helpCommandList = messagesFile.getStringList("jobs.help.format");

            for (String message : helpCommandList) {
                sender.sendMessage(message);
            }

            if (!sender.hasPermission("jobs.admin")) {
                return true;
            }

            List<String> helpCommandAdmin = messagesFile.getStringList("jobs.help.admin");

            for (String message : helpCommandAdmin) {
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

                if (!sender.hasPermission("jobs.admin")) {
                    break;
                }

                List<String> helpCommandAdmin = messagesFile.getStringList("jobs.help.admin");

                for (String message : helpCommandAdmin) {
                    sender.sendMessage(message);
                }
                break;

            case "reload":

                if (sender.hasPermission("jobs.admin")) {
                    sender.sendMessage(messagesFile.getString("error.no-permission"));
                }

                configFile.reload();
                messagesFile.reload();
                sender.sendMessage(messagesFile.getString("jobs.reload"));

            case "join":


                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs join [job]"));
                    return true;

                }
                String jobName = arguments[1].toLowerCase();

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
                break;

            case "leave-all":
                PlayerData playerDataLeaveAll = dataLoader.getPlayerJob(sender.getUniqueId());

                if (playerDataLeaveAll.getJobSize() == 0) {
                    sender.sendMessage(messagesFile.getString("error.dont-join-any-jobs"));
                    break;
                }

                playerDataLeaveAll.getJobsData().clear();
                playersFile.getJobData(sender.getUniqueId()).set("", "");
                break;

            case "browse":

                for (String message : messagesFile.getStringList("jobs.browse.list")) {
                    sender.sendMessage(message);
                }

                break;

            case "stats":
                PlayerData playerDataStats = dataLoader.getPlayerJob(sender.getUniqueId());

                if (playerDataStats.getJobsData().values().isEmpty()) {
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

                if (playerDataInfo.getJob(jobNameInfo) == null) {
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

            case "add-level":
                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs add-level [player] [job] [level]"));
                    return true;

                }

                Player targetAddLevel = Bukkit.getPlayer(arguments[1]);

                if (targetAddLevel == null) {
                    sender.sendMessage(messagesFile.getString("error.no-online"));
                    return true;

                }

                if (arguments.length < 3) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs add-level [player] [level]"));
                    return true;

                }

                PlayerData playerDataAddLevel = dataLoader.getPlayerJob(targetAddLevel.getUniqueId());

                String jobNameAddLevel = arguments[2];

                if (playersFile.isConfigurationSection("jobs." + jobNameAddLevel)) {
                    sender.sendMessage(messagesFile.getString("error.unknown-job")
                            .replace("%job%", configFile.getString("jobs." + jobNameAddLevel + ".name")));
                    return true;
                }

                String levelAddLevel = arguments[3];

                if (playerDataAddLevel.getJob(jobNameAddLevel) == null) {
                    playerDataAddLevel.addJob(jobNameAddLevel);
                }

                JobData jobDataAddLevel = playerDataAddLevel.getJob(jobNameAddLevel);

                jobDataAddLevel.setLevel(jobDataAddLevel.getLevel() + Integer.parseInt(levelAddLevel));
                jobDataAddLevel.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), jobDataAddLevel.getLevel() + Integer.parseInt(levelAddLevel)));

                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameAddLevel + ".level", jobDataAddLevel.getLevel());
                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameAddLevel + ".xp", 0);
                playersFile.save();

                sender.sendMessage(messagesFile.getString("jobs.add-level.message")
                        .replace("%level%", String.valueOf(jobDataAddLevel.getLevel() + Integer.parseInt(levelAddLevel)))
                        .replace("%job%", jobNameAddLevel)
                        .replace("%player%", targetAddLevel.getName()));
                break;

            case "set-level":
                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs -level [player] [job] [level]"));
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

                if (playersFile.isConfigurationSection("jobs." + jobNameSet)) {
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

                sender.sendMessage(messagesFile.getString("jobs.set-level.message")
                        .replace("%level%", level)
                        .replace("%job%", jobNameSet)
                        .replace("%player%", target.getName()));
                break;

            case "remove-level":

                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs set-level [player] [job] [level]"));
                    return true;

                }

                Player targetRemoveLevel = Bukkit.getPlayer(arguments[1]);

                if (targetRemoveLevel == null) {
                    sender.sendMessage(messagesFile.getString("error.no-online"));
                    return true;

                }

                if (arguments.length < 3) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs set-level [player] [level]"));
                    return true;

                }

                PlayerData playerDataRemoveLevel = dataLoader.getPlayerJob(targetRemoveLevel.getUniqueId());

                String jobNameRemoveLevel = arguments[2];

                if (playersFile.isConfigurationSection("jobs." + jobNameRemoveLevel)) {
                    sender.sendMessage(messagesFile.getString("error.unknown-job")
                            .replace("%job%", configFile.getString("jobs." + jobNameRemoveLevel + ".name")));
                    return true;
                }

                String levelRemoveLevel = arguments[3];

                if (playerDataRemoveLevel.getJob(jobNameRemoveLevel) == null) {
                    playerDataRemoveLevel.addJob(jobNameRemoveLevel);
                }

                JobData jobDataRemoveLevel = playerDataRemoveLevel.getJob(jobNameRemoveLevel);

                jobDataRemoveLevel.setLevel(jobDataRemoveLevel.getLevel() - Integer.parseInt(levelRemoveLevel));
                jobDataRemoveLevel.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), jobDataRemoveLevel.getLevel() - Integer.parseInt(levelRemoveLevel)));

                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameRemoveLevel + ".level", levelRemoveLevel);
                playersFile.setJobData(sender.getUniqueId(), "job-list." + jobNameRemoveLevel + ".xp", 0);
                playersFile.save();

                sender.sendMessage(messagesFile.getString("jobs.remove-level.message")
                        .replace("%level%", String.valueOf(jobDataRemoveLevel.getLevel() - Integer.parseInt(levelRemoveLevel)))
                        .replace("%job%", jobNameRemoveLevel)
                        .replace("%player%", targetRemoveLevel.getName()));
                break;

            case "set-multiplier":

                if (arguments.length < 2) {

                    sender.sendMessage(messagesFile.getString("error.no-argument")
                            .replace("%usage%", "/jobs set-multiplier [multiplier]"));
                    return true;

                }

                if (!StringUtils.isNumeric(arguments[2])){
                    sender.sendMessage(messagesFile.getString("error.unknown-number"));
                    return true;
                }

                dataLoader.setServerMultiplier(Double.parseDouble(arguments[2]));
                sender.sendMessage(messagesFile.getString("jobs.multiplier.set")
                        .replace("%multiplier%", arguments[2]));

                if (!configFile.getString("config.multiplier.broadcast").equalsIgnoreCase("none")) {
                    Bukkit.broadcastMessage(configFile.getString("config.multiplier.broadcast")
                            .replace("%multiplier%", arguments[2]));
                }

            default:
                sender.sendMessage(messagesFile.getString("error.unknown-argument"));
        }
        return true;
    }
}
