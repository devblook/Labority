package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.manager.VaultHookManager;
import me.bryang.workity.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JobActionRewardsManager implements JobManager {

    private final FileManager configFile;
    private final VaultHookManager vaultHookManager;


    public JobActionRewardsManager(PluginCore pluginCore) {
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.vaultHookManager = pluginCore.getManagerLoader().getVaultHookManager();
    }

    @Override
    public void doWorkAction(Player player, String jobName, String itemName, PlayerJobData playerJobData) {


        if (!configFile.getBoolean("config.rewards.enabled")) {
            return;
        }

        int level = playerJobData.getLevel();

        if (!configFile.isConfigurationSection("config.rewards." + level)) {
            return;
        }

        for (String format : configFile.getStringList("config.rewards." + level + ".format")) {

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
