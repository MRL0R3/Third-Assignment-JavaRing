package org.project.object.weapons;

import java.util.List;
import org.project.entity.Entity;
import org.project.object.Object;

/**
 * Abstract base class for all weapons in the game.
 * Implements core combat functionality, durability systems, and special abilities.
 */
public abstract class Weapon implements Object {

    // Weapon Attributes
    protected String name;         // Display name of the weapon
    protected int baseDamage;      // Base damage value
    protected int manaCost;        // Mana required for special abilities
    protected int durability;      // Current durability
    protected int maxDurability;   // Maximum durability capacity

    /**
     * Constructs a new weapon instance.
     * @param name       The weapon's display name
     * @param damage     Base damage value
     * @param manaCost   Mana cost for special abilities
     * @param durability Starting/Maximum durability
     */
    public Weapon(String name, int damage, int manaCost, int durability) {
        this.name = name;
        this.baseDamage = damage;
        this.manaCost = manaCost;
        this.durability = this.maxDurability = durability;
    }

    /**
     * Gets the base damage value before modifiers.
     * @return The weapon's base damage
     */
    public int getDamage() {
        return baseDamage;
    }

    /**
     * Core combat method - attacks a target entity.
     * Decreases durability with each use.
     * @param target The entity to attack
     */
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

    /**
     * Calculates final damage (can be overridden for custom damage formulas).
     * @return The calculated damage amount
     */
    protected int calculateDamage() {
        return baseDamage; // Default implementation uses base damage
    }

    /**
     * Abstract method for weapon special abilities.
     * Must be implemented by concrete weapon classes.
     * @param targets List of entities affected by the ability
     */
    public abstract void specialAbility(List<Entity> targets);

    // ========== DURABILITY SYSTEM ========== //

    /**
     * Fully repairs the weapon.
     */
    @Override
    public void repair() {
        durability = maxDurability;
        System.out.println(name + " has been repaired!");
    }

    /**
     * Checks if the weapon is broken.
     * @return true if durability <= 0, false otherwise
     */
    @Override
    public boolean isBroken() {
        return durability <= 0;
    }

    // ========== GETTER METHODS ========== //

    /**
     * @return The weapon's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Provides a formatted weapon description.
     * @return String containing name and damage value
     */
    @Override
    public String getDescription() {
        return String.format("%s (%d DMG)", name, baseDamage);
    }

    /**
     * Weapons are not consumable items.
     * @return Always false
     */
    @Override
    public boolean isConsumable() {
        return false;
    }

    /**
     * Calculates the weapon's in-game value.
     * @return Base damage * 8 (gold value)
     */
    @Override
    public int getValue() {
        return baseDamage * 8;
    }

    /**
     * @return Current durability
     */
    public int getDurability() {
        return durability;
    }
}