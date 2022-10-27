package me.bryang.workity.listener.plugin;

import me.bryang.workity.PluginCore;
import me.bryang.workity.action.Action;
import me.bryang.workity.data.JobData;
import me.bryang.workity.events.JobsEvent;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.VaultHookManager;
import me.bryang.workity.manager.file.FileDataManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.TextUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobListener implements Listener {

    private final VaultHookManager vaultHookManager;

    private final FileManager configFile;
    private final FileManager messagesFile;
    private final FileDataManager playersFile;

    private final DataLoader dataLoader;

    public JobListener(PluginCore pluginCore) {

        this.vaultHookManager = pluginCore.getManagerLoader().getVaultHookManager();

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();

        this.dataLoader = pluginCore.getDataLoader();
    }

    @EventHandler
    public void onWork(JobsEvent event) {


        for (String jobs : configFile.getConfigurationSection("jobs").getKeys(false)) {

            Action action = event.getAction();

            if (!configFile.getString("jobs." + jobs + ".type").equalsIgnoreCase(action.getType())) {
                continue;
            }

            String dataRequired;

            if (action.getEntity() != null) {
                dataRequired = action.getEntity().getType().name();
            } else {
                dataRequired = action.getItemStack().getType().name();
            }

            if (!configFile.isConfigurationSection("jobs." + jobs + ".items." + dataRequired)) {
                continue;
            }

            JobData jobData = dataLoader.getPlayerJob(event.getTarget()).getJob(jobs);

            if (jobData == null) {
                return;
            }

            Player player = Bukkit.getPlayer(event.getTarget());

            double multiplier;


            if (vaultHookManager.getPermission().hasGroupSupport()) {
                if (!configFile.isInt("config.multiplier.group." +
                        vaultHookManager.getPermission().getPrimaryGroup(player))) {

                    multiplier = configFile.getInt("config.multiplier.default");

                } else {
                    multiplier = configFile.getInt("config.multiplier.group." +
                            vaultHookManager.getPermission().getPrimaryGroup(player));
                }
            } else {
                multiplier = configFile.getInt("config.multiplier.default");
            }

            if (dataLoader.getServerMultiplier() > 0) {
                multiplier = multiplier + dataLoader.getServerMultiplier();
            }

            double moneyReward = TextUtils.calculateDoubleNumber(configFile.getString("config.formula.gain-money")
                            .replace("%money%",
                                    configFile.getString("jobs." + jobs + ".items." + dataRequired + ".money")),
                    jobData.getLevel()) * multiplier;

            int xpReward = TextUtils.calculateNumber(configFile.getString("config.formula.gain-xp")
                            .replace("%xp%",
                                    configFile.getString("jobs." + jobs + ".items." + dataRequired + ".xp")),
                    jobData.getLevel()) * (int) multiplier;

            vaultHookManager.getEconomy().depositPlayer(player, moneyReward);

            jobData.setXPPoints(jobData.getXpPoints() + xpReward);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                    configFile.getString("config.action-bar.gain-rewards")
                            .replace("%money%", String.valueOf(moneyReward))
                            .replace("%xp%", String.valueOf(xpReward))));

            playersFile.setJobData(player.getUniqueId(), "job-list." + jobData.getName() + ".level", "");
            playersFile.setJobData(player.getUniqueId(), "job-list." + jobData.getName() + ".xp", "");
            playersFile.save();

            if (jobData.getMaxXP() > jobData.getXpPoints()) {
                return;
            }

            if (jobData.getLevel() == configFile.getInt("config.max-level-jobs")) {

                jobData.setXPPoints(jobData.getMaxXP());
                player.sendMessage(messagesFile.getString("error.max-level"));
                return;
            }

            jobData.setLevel(jobData.getLevel() + 1);
            jobData.setXPPoints(jobData.getMaxXP() - jobData.getXpPoints());
            jobData.setMaxXP(
                    TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), jobData.getLevel()));

            player.sendMessage(messagesFile.getString("jobs.gain.level")
                    .replace("%new_level%", String.valueOf(jobData.getLevel())));

            if (!configFile.getBoolean("jobs." + jobs + ".global-stats")) {
                if (configFile.getBoolean("jobs." + jobs + ".items." + dataRequired + ".enabled-stats")) {

                    int itemDataStats = playersFile.getJobData(player.getUniqueId()).getInt(".stats", -1);

                    if (itemDataStats == -1) {
                        playersFile.setJobData(player.getUniqueId(), "job-list." + jobData + ".items." + dataRequired + "stats.", 1);
                    } else {

                        playersFile.setJobData(player.getUniqueId(), "job-list." + jobData + ".items." + dataRequired + "stats", itemDataStats + 1);
                    }
                }
            } else {

            }


            if (!configFile.getBoolean("config.rewards.enabled")) {
                return;
            }

            if (!configFile.isConfigurationSection("config.rewards." + jobData.getLevel())) {
                return;
            }

            for (String format : configFile.getStringList("config.rewards." + jobData.getLevel() + ".format")) {

                if (format.startsWith("[BROADCAST]")) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format.substring(11))
                            .replace("%player%", player.getName()));
                }

                if (format.startsWith("[COMMAND]")) {
                    player.performCommand(format.substring(9)
                            .replace("%player%", player.getName()));
                }

                if (format.startsWith("[MONEY]")) {
                    vaultHookManager.getEconomy().depositPlayer(player, Double.parseDouble(format.substring(7)));
                }
            }
        }
    }

}




