package me.bryangaming.labority.action;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class Action {

    private final JobType jobType;

    private final Entity entity;
    private final ItemStack itemStack;

    public Action(JobType jobType, Entity entity) {

        this.jobType = jobType;

        this.entity = entity;
        this.itemStack = null;
    }

    public Action( JobType jobType, ItemStack itemStack){

        this.jobType = jobType;

        this.itemStack = itemStack;
        this.entity = null;
    }


    public String getType() {
        return jobType.name();
    }

    public Entity getEntity() {
        return entity;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
