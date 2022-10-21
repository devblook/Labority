package me.bryangaming.labority.commands;

import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.data.PlayerData;
import me.bryangaming.labority.loader.DataLoader;
import me.bryangaming.labority.manager.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JobsCommand implements CommandExecutor {

    private final FileManager configFile;
    private final FileManager messagesFile;

    private final DataLoader dataLoader;

    public JobsCommand(PluginCore pluginCore){

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
        this.dataLoader = pluginCore.getDataLoader();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String idk, String[] arguments) {

        if (!(commandSender instanceof Player)){

            System.out.println(messagesFile.getString("error.console"));
            return false;

        }

        Player sender = (Player) commandSender;

        if (arguments.length < 1){

            List<String> helpCommandList =  messagesFile.getStringList("jobs.help");

            for (String message : helpCommandList){
                sender.sendMessage(message);
            }


        }

        switch (arguments[0]){
            case "help":
                List<String> helpCommandList =  messagesFile.getStringList("jobs.help");

                for (String message : helpCommandList){
                    sender.sendMessage(message);
                }
                break;

            case "reload":
                configFile.reload();
                messagesFile.reload();
                sender.sendMessage(messagesFile.getString("jobs.reload"));

                break;

            case "join":

                String jobName = arguments[1];

                if (jobName == null){

                    sender.sendMessage(messagesFile.getString("error.no-argument").replace("%usage%", "/jobs join [trabajo]"));
                    return false;

                }

                if (configFile.getConfigurationSection("jobs." +  jobName) == null){

                    sender.sendMessage(messagesFile.getString("error.unknown-job").replace("%job%", jobName));
                    return false;

                }

                PlayerData playerData = dataLoader.getPlayerJob(sender.getUniqueId());

                if (playerData.hasTheJob(jobName)){

                    sender.sendMessage(messagesFile.getString("error.already-have-job").replace("%job%", jobName));
                    return false;

                }

                playerData.addJob(jobName);

            default:
                sender.sendMessage(messagesFile.getString("error.unknown-argument"));
        }
        return false;
    }
}
