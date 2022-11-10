package me.bryang.workity.events;


import me.bryang.workity.action.Action;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class JobExecuteEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final UUID targetUniqueId;

    private final Action action;

    public JobExecuteEvent(UUID targetUniqueId, Action action) {
        this.action = action;
        this.targetUniqueId = targetUniqueId;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public UUID getTarget() {
        return targetUniqueId;
    }

    public Action getAction() {
        return action;
    }


}
