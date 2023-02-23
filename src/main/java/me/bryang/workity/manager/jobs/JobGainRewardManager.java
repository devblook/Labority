package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.data.jobs.BlockJobData;
import me.bryang.workity.database.Database;
import me.bryang.workity.loader.DataLoader;
import me.bryang.workity.manager.VaultHookManager;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.MathLevelsUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class JobGainRewardManager implements JobManager {

    private final DataLoader dataLoader;

    private final FileManager configFile;
    private final Database database;

    private final VaultHookManager vaultHookManager;

    public JobGainRewardManager(PluginCore pluginCore) {

        this.dataLoader = pluginCore.getDataLoader();

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.database = pluginCore.getDatabaseLoader().getDatabase();

        this.vaultHookManager = pluginCore.getManagerLoader().getVaultHookManager();
    }

    @Override
    public void doWorkAction(Player player, String jobName, String itemName, PlayerJobData playerJobData) {


        BlockJobData blockJobData = dataLoader.getJob(jobName).getBlockData(itemName);

        double multiplier = playerJobData.getMultiplier();

        double moneyReward = MathLevelsUtils.calculateDoubleNumber(configFile.getString("config.formula.gain-money")

                        .replace("%money%", String.valueOf(blockJobData.getGainMoney())),
                playerJobData.getLevel()) * multiplier;

        double xpReward = MathLevelsUtils.calculateNumber(configFile.getString("config.formula.gain-xp")
                        .replace("%xp%", String.valueOf(blockJobData.getGainXP())),
                playerJobData.getLevel()) * (int) multiplier;

        vaultHookManager.getEconomy().depositPlayer(player, moneyReward);

        playerJobData.setXPPoints(playerJobData.getXpPoints() + xpReward);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                configFile.getString("config.action-bar.gain-rewards")
                        .replace("%money%", String.valueOf(moneyReward))
                        .replace("%xp%", String.valueOf(xpReward))));

        database
                .initActivity(player.getUniqueId(), true)
                .insertJobData(jobName, "xp", playerJobData.getXpPoints() + xpReward)
                .savePlayerAndCloseActivity();
    }
}
