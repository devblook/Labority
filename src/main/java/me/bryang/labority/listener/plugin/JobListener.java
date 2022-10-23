package me.bryang.labority.listener.plugin;

import me.bryang.labority.PluginCore;
import me.bryang.labority.action.Action;
import me.bryang.labority.data.JobData;
import me.bryang.labority.events.JobsEvent;
import me.bryang.labority.loader.DataLoader;
import me.bryang.labority.manager.VaultHookManager;
import me.bryang.labority.manager.file.FileDataManager;
import me.bryang.labority.manager.file.FileManager;
import me.bryang.labority.utils.TextUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
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

            vaultHookManager.getEconomy().depositPlayer(player,
                    TextUtils.calculateNumber(configFile.getString("config.formula.gain-money")
                            .replace("%money%", configFile.getString("jobs." + jobs + ".items." + dataRequired + ".money")), jobData.getLevel()));

            jobData.setXPPoints(jobData.getXpPoints() + TextUtils.calculateNumber(configFile.getString("config.formula.gain-xp")
                    .replace("%xp%", configFile.getString("jobs." + jobs + ".items." + dataRequired + ".xp")), jobData.getLevel()));


            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(configFile.getString("config.action-bar.gain-rewards")
                    .replace("%money%", String.valueOf(TextUtils.calculateNumber(configFile.getString("config.formula.gain-money")
                            .replace("%money%", configFile.getString("jobs." + jobs + ".items." + dataRequired + ".money")), jobData.getLevel())))
                    .replace("%xp%", String.valueOf(TextUtils.calculateNumber(configFile.getString("config.formula.gain-xp")
                            .replace("%xp%", configFile.getString("jobs." + jobs + ".items." + dataRequired + ".xp")), jobData.getLevel())))));

            playersFile.setJobData(player.getUniqueId(), "job-list." + jobData.getName() + ".level", "");
            playersFile.setJobData(player.getUniqueId(), "job-list." + jobData.getName() + ".xp", "");
            playersFile.save();

            if (jobData.getMaxXP() <= jobData.getXpPoints()) {

                jobData.setLevel(jobData.getLevel() + 1);
                jobData.setXPPoints(jobData.getMaxXP() - jobData.getXpPoints());
                jobData.setMaxXP(TextUtils.calculateNumber(configFile.getString("config.formula.max-xp"), jobData.getLevel()));

                player.sendMessage(messagesFile.getString("jobs.gain.level")
                        .replace("%new_level%", String.valueOf(jobData.getLevel())));
            }


        }
    }
}
