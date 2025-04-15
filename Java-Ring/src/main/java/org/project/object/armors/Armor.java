package org.project.object.armors;

import org.project.object.Object;


public abstract class Armor implements Object {

    protected String name;
    private int defense;
    protected int durability;
    final int maxDurability;

    public Armor(String name, int defense, int durability) {
        this.name = name;
        this.defense = defense;
        this.durability = this.maxDurability = durability;
    }

    public int protect(int incomingDamage) {
        int damageReduction = Math.min(defense, incomingDamage / 2);
        int damageTaken = Math.max(1, incomingDamage - damageReduction);

        durability -= incomingDamage / 10;
        if (durability < 0) {
            durability = 0;
        }

        return damageTaken;
    }


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
        return String.format("%s (%d DEF)", name, defense);
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    @Override
    public int getValue() {
        return defense * 12;
    }

    public int getDurability() {
        return durability;
    }
}
