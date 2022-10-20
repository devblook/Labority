package me.bryangaming.labority.commands;

import me.bryangaming.labority.PluginCore;
import me.bryangaming.labority.manager.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JobsCommand implements CommandExecutor {

    private final FileManager configFile;
    private final FileManager messagesFile;

    public JobsCommand(PluginCore pluginCore){

        this.configFile = pluginCore.getFilesLoader().getConfigFile();
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String idk, String[] arguments) {

        if (!(sender instanceof Player)){

            System.out.println(messagesFile.getString("error.console"));
            return false;

        }

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

            default:
                sender.sendMessage(messagesFile.getString("error.unknown-argument"));
        }
        return false;
    }
}
