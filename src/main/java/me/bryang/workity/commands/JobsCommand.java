package me.bryang.workity.commands;


import me.bryang.workity.PluginCore;
import me.bryang.workity.data.JobData;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.TextUtils;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@Command(

        names = {"jobs", "work", "workity"},
        desc = "Main command.")

public class JobsCommand implements CommandClass {

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

    @Command(
            names = "")

    public boolean onMainSubCommand(

            @Sender Player sender) {

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

    @Command(
            names = "help")

    public boolean onHelpSubCommand(

            @Sender Player sender) {

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

    @Command(
            names = "reload",
            permission = "jobs.admin")

    public boolean onReloadSubCommand(

            @Sender Player sender) {

        configFile.reload();
        messagesFile.reload();
        sender.sendMessage(messagesFile.getString("jobs.reload"));
        return true;
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

    @Command(
            names = "leave-all")

    public boolean onLeaveSubCommand(

            @Sender Player sender) {

        PlayerData playerDataLeaveAll = dataLoader.getPlayerJob(sender.getUniqueId());

        if (playerDataLeaveAll.getJobSize() == 0) {
            sender.sendMessage(messagesFile.getString("error.dont-join-any-jobs"));
            return true;
        }

        playerDataLeaveAll.getJobsMap().clear();

        for (String text : playersFile.getJobsKeys(sender.getUniqueId())) {
            playersFile.setJobData(sender.getUniqueId(), "job-list." + text + ".level", "");
            playersFile.setJobData(sender.getUniqueId(), "job-list." + text + ".xp", "");
            playersFile.save();
        }
        sender.sendMessage(messagesFile.getString("jobs.leave-all.message"));
        return true;
    }

    @Command(
            names = "browse")

    public boolean onBrowseSubCommand(

            @Sender Player sender) {

        for (String message : messagesFile.getStringList("jobs.browse.list")) {
            sender.sendMessage(message);
        }
        return true;
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
                            .replace("%gain_money%", String.valueOf(configFile.getInt("jobs." + jobNameInfo + ".items." + item + ".money")))
                            .replace("%gain_xp%", String.valueOf(configFile.getInt("jobs." + jobNameInfo + ".items." + item + ".xp"))));
                }
            }


            return true;
        }

        for (String message : messagesFile.getStringList("jobs.info.message")) {

            if (!message.contains("%job_format%")) {
                sender.sendMessage(message.replace("%job_name%", configFile.getString("jobs." + jobNameInfo + ".name")));
                continue;
            }

            for (String item : configFile.getConfigurationSection("jobs." + jobNameInfo + ".items").getKeys(false)) {

                sender.sendMessage(message
                        .replace("%job_format%", "")
                        .replace("%item_name%", item)
                        .replace("%gain_money%", String.valueOf(configFile.getInt("jobs." + jobNameInfo + ".items." + item + ".money")))
                        .replace("%gain_xp%", String.valueOf(configFile.getInt("jobs." + jobNameInfo + ".items." + item + ".xp"))));
            }
        }


        return true;
    }

    @Command(
            names = "add-level",
            permission = "jobs.admin")

    public boolean onAddLevelSubCommand(

            @Sender Player sender,
            @OptArg("") String targetArgument,
            @OptArg("") String jobArgument,
            @OptArg String levelArgument) {

        if (targetArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs add-level [player] [job] [level]"));
            return true;

        }

        Player target = Bukkit.getPlayer(targetArgument);

        if (target == null) {
            sender.sendMessage(messagesFile.getString("error.no-online"));
            return true;

        }

        if (jobArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs add-level [player] [level]"));
            return true;

        }

        PlayerData playerData = dataLoader.getPlayerJob(target.getUniqueId());

        if (playersFile.isConfigurationSection("jobs." + jobArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", configFile.getString("jobs." + jobArgument + ".name")));
            return true;
        }

        if (levelArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs add-level [player] [level]"));
            return true;

        }

        if (!StringUtils.isNumeric(levelArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-number"));
            return true;
        }

        if (Integer.parseInt(levelArgument) < 0) {
            sender.sendMessage(messagesFile.getString("error.negative-number"));
            return true;
        }

        if (playerData.getJob(jobArgument) == null) {
            playerData.addJob(jobArgument);
        }

        JobData jobData = playerData.getJob(jobArgument);

        jobData.setLevel(jobData.getLevel() + Integer.parseInt(levelArgument));
        jobData.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), jobData.getLevel() + Integer.parseInt(levelArgument)));

        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".level", jobData.getLevel());
        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".xp", 0);
        playersFile.save();

        sender.sendMessage(messagesFile.getString("jobs.add-level.message")
                .replace("%level%", String.valueOf(jobData.getLevel() + Integer.parseInt(levelArgument)))
                .replace("%job%", jobArgument)
                .replace("%player%", target.getName()));
        return true;
    }

    @Command(
            names = "set-level",
            permission = "jobs.admin")

    public boolean onSetLevelSubCommand(

            @Sender Player sender,
            @OptArg("") String targetArgument,
            @OptArg("") String jobArgument,
            @OptArg String levelArgument) {

        if (targetArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-level [player] [job] [level]"));
            return true;

        }

        Player target = Bukkit.getPlayer(targetArgument);

        if (target == null) {
            sender.sendMessage(messagesFile.getString("error.no-online"));
            return true;

        }

        if (jobArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-level [player] [level]"));
            return true;

        }

        PlayerData playerData = dataLoader.getPlayerJob(target.getUniqueId());

        if (playersFile.isConfigurationSection("jobs." + jobArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", configFile.getString("jobs." + jobArgument + ".name")));
            return true;
        }

        if (levelArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-level [player] [level]"));
            return true;

        }


        if (!StringUtils.isNumeric(levelArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-number"));
            return true;
        }

        if (Integer.parseInt(levelArgument) < 0) {
            sender.sendMessage(messagesFile.getString("error.negative-number"));
            return true;
        }

        if (playerData.getJob(jobArgument) == null) {
            playerData.addJob(jobArgument);
        }

        JobData jobData = playerData.getJob(jobArgument);

        jobData.setLevel(Integer.parseInt(levelArgument));
        jobData.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), Integer.parseInt(levelArgument)));

        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".level", levelArgument);
        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".xp", 0);
        playersFile.save();

        sender.sendMessage(messagesFile.getString("jobs.set-level.message")
                .replace("%level%", levelArgument)
                .replace("%job%", jobArgument)
                .replace("%player%", target.getName()));
        return true;
    }

    @Command(
            names = "remove-level",
            permission = "jobs.admin")

    public boolean onRemoveLevelSubCommand(

            @Sender Player sender,
            @OptArg("") String targetArgument,
            @OptArg("") String jobArgument,
            @OptArg String levelArgument) {

        if (targetArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [job] [level]"));
            return true;

        }

        Player targetRemoveLevel = Bukkit.getPlayer(targetArgument);

        if (targetRemoveLevel == null) {
            sender.sendMessage(messagesFile.getString("error.no-online"));
            return true;

        }

        if (jobArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [level]"));
            return true;

        }

        PlayerData playerDataRemoveLevel = dataLoader.getPlayerJob(targetRemoveLevel.getUniqueId());

        if (playersFile.isConfigurationSection("jobs." + jobArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-job")
                    .replace("%job%", configFile.getString("jobs." + jobArgument + ".name")));
            return true;
        }

        if (levelArgument.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs remove-level [player] [level]"));
            return true;

        }


        if (!StringUtils.isNumeric(levelArgument)) {
            sender.sendMessage(messagesFile.getString("error.unknown-number"));
            return true;
        }

        if (Integer.parseInt(levelArgument) < 0) {
            sender.sendMessage(messagesFile.getString("error.negative-number"));
            return true;
        }

        if (playerDataRemoveLevel.getJob(jobArgument) == null) {
            playerDataRemoveLevel.addJob(jobArgument);
        }

        JobData jobDataRemoveLevel = playerDataRemoveLevel.getJob(jobArgument);

        if (jobDataRemoveLevel.getLevel() - Integer.parseInt(levelArgument) < 0) {
            sender.sendMessage(messagesFile.getString("error.minor-0"));
            return true;
        }

        jobDataRemoveLevel.setLevel(jobDataRemoveLevel.getLevel() - Integer.parseInt(levelArgument));
        jobDataRemoveLevel.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), jobDataRemoveLevel.getLevel() - Integer.parseInt(levelArgument)));

        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".level", levelArgument);
        playersFile.setJobData(sender.getUniqueId(), "job-list." + jobArgument + ".xp", 0);
        playersFile.save();

        sender.sendMessage(messagesFile.getString("jobs.remove-level.message")
                .replace("%level%", levelArgument)
                .replace("%job%", jobArgument)
                .replace("%player%", targetRemoveLevel.getName()));
        return true;
    }

    @Command(
            names = "set-multiplier",
            permission = "jobs.admin")

    public boolean onSetMultiplierSubCommand(
            
            @Sender Player sender,
            @OptArg("") String multiplier) {

        if (!sender.hasPermission("jobs.admin")) {
            sender.sendMessage(messagesFile.getString("error.no-permission"));
            return true;
        }


        if (multiplier.isEmpty()) {

            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/jobs set-multiplier [multiplier]"));
            return true;

        }

        if (!StringUtils.isNumeric(multiplier)) {
            sender.sendMessage(messagesFile.getString("error.unknown-number"));
            return true;
        }

        dataLoader.setServerMultiplier(Double.parseDouble(multiplier));
        sender.sendMessage(messagesFile.getString("jobs.multiplier.set")
                .replace("%multiplier%", multiplier));

        if (!configFile.getString("config.multiplier.broadcast").equalsIgnoreCase("none")) {
            Bukkit.broadcastMessage(configFile.getString("config.multiplier.broadcast")
                    .replace("%multiplier%", multiplier));
        }
        return true;
    }

}