package me.bryang.workity.manager.jobs;

import me.bryang.workity.PluginCore;
import me.bryang.workity.data.PlayerJobData;
import me.bryang.workity.database.Database;
import me.bryang.workity.manager.file.FileManager;
import me.bryang.workity.utils.MathLevelsUtils;
import org.bukkit.entity.Player;

public class JobIncreaseLevelManager implements JobManager {

    private final FileManager configFile;
    private final FileManager messagesFile;
    private final Database database;


    public JobIncreaseLevelManager(PluginCore pluginCore) {
        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();

        this.database = pluginCore.getDatabaseLoader().getDatabase();
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
        playerJobData.setXPPoints(0);
        playerJobData.setMaxXP(
                MathLevelsUtils.calculateNumber(configFile.getString("config.formula.max-xp"), playerJobData.getLevel()));

        database
                .insertJobData(player.getUniqueId(), jobName, "level", playerJobData.getLevel() + 1)
                .insertJobData(player.getUniqueId(), jobName, "xp", 0)
                .save();

        player.sendMessage(messagesFile.getString("jobs.gain.level")
                .replace("%new_level%", String.valueOf(playerJobData.getLevel())));
    }
}
