package me.bryang.workity.loader.command;

import me.bryang.workity.PluginCore;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

public class CommandTranslationLoader implements TranslationProvider {

    private final FileManager messagesFile;
;

    public CommandTranslationLoader(PluginCore pluginCore) {
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
    }


    @Override
    public String getTranslation(Namespace namespace, String key) {
        switch (key) {
            case "sender.only-player":
                return messagesFile.getString("error.no-console");
            case "player.offline":
                return messagesFile.getString("error.no-online");
            case "command.no-permission":
                return messagesFile.getString("error.no-permission");
            case "invalid.integer":
                return messagesFile.getString("error.unknown-number");
        }
        return "Error: Si ves este mensaje, avisa con en el discord de soporte con @ERROR101";
    }
}
