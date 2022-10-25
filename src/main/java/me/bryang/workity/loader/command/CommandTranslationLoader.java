package me.bryang.workity.loader.command;

import me.bryang.workity.PluginCore;
import me.bryang.workity.manager.file.FileManager;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import java.util.Map;

public class CommandTranslationLoader implements TranslationProvider {

    private final FileManager messagesFile;
    private Map<String, String> translations;

    public CommandTranslationLoader(PluginCore pluginCore){
        this.messagesFile = pluginCore.getFilesLoader().getMessagesFile();
        setup();
    }

    public void setup() {
        translations.put("command.subcommand.invalid", "1. The subcommand %s doesn't exist!");
        translations.put("command.no-permission", "2. No permission.");
        translations.put("argument.no-more", "3. No more arguments were found, size: %s position: %s");
        translations.put("sender.unknown", "5. Unknown command sender!");
    }

    @Override
    public String getTranslation(Namespace namespace, String key) {
        switch (key){
            case "sender.only-player":
                return messagesFile.getString("error.no-console");
            case "player.offline":
                return messagesFile.getString("error.no-online");
            case "command.no-permission":
                return messagesFile.getString("error.no-permission");
        }
        return translations.get(key);
    }
}
