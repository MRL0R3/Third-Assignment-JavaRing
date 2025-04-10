package org.project.object.consumables;

import org.project.entity.Entity;
import org.project.object.Object;

public abstract class Consumable implements Object {

    protected String name;
    protected String description;
    protected int value;
    protected int remainingUses;

    public Consumable(String name, String desc, int value, int uses) {
        this.name = name;
        this.description = desc;
        this.value = value;
        this.remainingUses = uses;
    }

    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    @Override
    public int getValue() {
        return value;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    protected void consumeUse() {
        if (remainingUses > 0) {
            remainingUses--;
        }
    }
}
