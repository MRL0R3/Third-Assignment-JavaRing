package org.project.object.weapons;

import java.util.List;

import org.project.entity.Entity;
import org.project.object.Object;

public abstract class Weapon implements Object {

    protected String name;
    protected int baseDamage;
    protected int manaCost;
    protected int durability;
    protected int maxDurability;

    public Weapon(String name, int damage, int manaCost, int durability) {
        this.name = name;
        this.baseDamage = damage;
        this.manaCost = manaCost;
        this.durability = this.maxDurability = durability;
    }
    public int getDamage() {
        return baseDamage;
    }
    // Core combat method
    @Override
    public void use(Entity target) {
        if (durability > 0) {
            int damage = calculateDamage();
            target.takeDamage(damage);
            durability--;
            System.out.printf("%s dealt %d damage! (Durability: %d/%d)\n",
                    name, damage, durability, maxDurability);
        } else {
            System.out.println(name + " is broken!");
        }
    }

    protected int calculateDamage() {
        return baseDamage;
    }

    // Special ability for weapons
    public abstract void specialAbility(List<Entity> targets);

    // Repair system
    @Override
    public void repair() {
        durability = maxDurability;
        System.out.println(name + " has been repaired!");
    }

    @Override
    public boolean isBroken() {
        return durability <= 0;
    }

    // Standard getters
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return String.format("%s (%d DMG)", name, baseDamage);
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    @Override
    public int getValue() {
        return baseDamage * 8;
    }

    public int getDurability() {
        return durability;
    }
}
