package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.TextUtils;
import org.bukkit.entity.Player;

public class JobIncreaseLevelManager implements WorkAction {

    private final FileManager configFile;
    private final FileManager messagesFile;


    public JobIncreaseLevelManager(PluginCore pluginCore) {
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
    }

    @Override
    public void doWorkAction(Player player, String jobName, String itemName, PlayerJobData playerJobData) {

        if (playerJobData.getMaxXP() >= playerJobData.getXpPoints()) {
            return;
        }

        if (playerJobData.getLevel() == configFile.getInt("config.max-level-jobs")) {

            playerJobData.setXPPoints(playerJobData.getMaxXP());
            player.sendMessage(messagesFile.getString("error.max-level"));
            return;
        }

        playerJobData.setLevel(playerJobData.getLevel() + 1);
        playerJobData.setXPPoints(playerJobData.getMaxXP() - playerJobData.getXpPoints());
        playerJobData.setMaxXP(
                TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), playerJobData.getLevel()));

        player.sendMessage(messagesFile.getString("jobs.gain.level")
                .replace("%new_level%", String.valueOf(playerJobData.getLevel())));
    }
}
