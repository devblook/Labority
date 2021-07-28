package me.bryangaming.labority.event;


import me.bryangaming.labority.action.Action;
import me.bryangaming.labority.action.JobType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JobsEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final Player target;
    private final Action action;

    public JobsEvent(Player target, Action action){
        this.action = action;
        this.target = target;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getSender() {
        return target;
    }

    public Action getAction() {
        return action;
    }
}
