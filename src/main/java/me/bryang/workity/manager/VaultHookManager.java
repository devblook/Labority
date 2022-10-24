package me.bryang.workity.manager;

import me.bryang.workity.Workity;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHookManager {

    private final Workity workity;

    private Economy economy;

    public VaultHookManager(Workity workity) {
        this.workity = workity;
    }

    public void load() {

        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {

            System.out.println("[Labority] Error: You need Vault to load the plugin");
            Bukkit.getPluginManager().disablePlugin(workity);
            return;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) {
            System.out.println("[Labority] Error: The economy doesn't loaded correctly.");
            return;
        }

        economy = rsp.getProvider();

    }

    public Economy getEconomy() {
        return economy;
    }


}
