package me.bryang.workity.loader;

import me.bryang.workity.Workity;
import me.bryang.workity.api.Loader;
import me.bryang.workity.data.PlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataLoader implements Loader {

    private final Map<UUID, PlayerData> playerJobDataMap = new HashMap<>();
    private final Workity workity;
    double serverMultiplier;

    public DataLoader(Workity workity) {
        this.workity = workity;
    }

    @Override
    public void load() {
        serverMultiplier = 0;
        workity.getLogger().info(" Data loaded.");

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
