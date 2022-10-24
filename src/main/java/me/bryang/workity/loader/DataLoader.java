package me.bryang.workity.loader;

import me.bryang.workity.PluginCore;
import me.bryang.workity.api.Loader;
import me.bryang.workity.data.PlayerData;
import me.bryang.workity.manager.file.FileManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataLoader implements Loader {

    private final Map<UUID, PlayerData> playerJobDataMap = new HashMap<>();

    double serverMultiplier;

    @Override
    public void load() {
        serverMultiplier = 0;
        System.out.println("[Workity] Data loaded.");

    }

    public void createPlayerJob(UUID uuid) {
        playerJobDataMap.put(uuid, new PlayerData());
    }


    public PlayerData getPlayerJob(UUID uuid) {
        return playerJobDataMap.get(uuid);
    }


    public double getServerMultiplier() {
        return serverMultiplier;
    }

    public void setServerMultiplier(double serverMultiplier) {
        this.serverMultiplier = serverMultiplier;
    }
}
