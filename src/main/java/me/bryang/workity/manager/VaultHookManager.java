package me.bryang.workity.manager;

import me.bryang.workity.Workity;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHookManager {

    private final Workity workity;

    private Economy economy;
    private Permission permission;

    public VaultHookManager(Workity workity) {
        this.workity = workity;
    }

    public void load() {

        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {

            Bukkit.getLogger("[Workity] Error: You need Vault to load the plugin");
            Bukkit.getPluginManager().disablePlugin(workity);
            return;
        }

        RegisteredServiceProvider<Economy> rse =
                Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        RegisteredServiceProvider<Permission> rsp =
                Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

        if (rse == null) {
            Bukkit.getLogger("[Workity] Error: The economy doesn't loaded correctly.");
            return;
        }

        if (rsp == null) {
            Bukkit.getLogger("[Workity] Error: The permission doesn't loaded correctly.");
            return;
        }


        economy = rse.getProvider();
        permission = rsp.getProvider();

    }

    public Economy getEconomy() {
        return economy;
    }

    public Permission getPermission() {
        return permission;
    }

}
