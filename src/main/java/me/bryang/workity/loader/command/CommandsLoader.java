package me.bryang.workity.loader.command;

import me.bryang.workity.PluginCore;
import me.bryang.workity.api.Loader;
import me.bryang.workity.commands.JobsCommand;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;


public class CommandsLoader implements Loader {

    private final PluginCore pluginCore;

    public CommandsLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {

        CommandManager commandManager = new BukkitCommandManager("Workity");
        commandManager.getTranslator().setProvider(new CommandTranslationLoader(pluginCore));

        PartInjector partInjector = PartInjector.create();

        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());


        AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        commandManager.registerCommands(builder.fromClass(new JobsCommand(pluginCore)));

        System.out.println("[Workity] Commands loaded.");

    }
}
