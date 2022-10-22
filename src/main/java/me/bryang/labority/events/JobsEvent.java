package me.bryang.labority.events;


import me.bryang.labority.action.Action;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class JobsEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final UUID target;
    private final Action action;

    public JobsEvent(UUID target, Action action){

        this.action = action;
        this.target = target;

    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public UUID getTarget() {
        return target;
    }

    public Action getAction() {
        return action;
    }
}
