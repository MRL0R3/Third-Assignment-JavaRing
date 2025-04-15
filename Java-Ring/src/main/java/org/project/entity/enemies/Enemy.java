package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.object.weapons.Weapon;

/**
 * Abstract base class for all enemy entities in the game.
 * Provides common functionality and attributes shared by all enemies.
 */
public abstract class Enemy implements Entity {

    // Core enemy attributes
    protected final String name;      // Enemy's display name
    protected int health;            // Current health points
    protected int maxHealth;         // Maximum health capacity
    protected Weapon weapon;         // Equipped weapon
    protected int expReward;         // Experience granted when defeated

    /**
     * Constructs a new Enemy instance.
     *
     * @param name       The name of the enemy
     * @param health     Initial/maximum health points
     * @param weapon     The weapon this enemy uses
     * @param expReward  Experience points awarded when defeated
     */
    public Enemy(String name, int health, Weapon weapon, int expReward) {
        this.name = name;
        this.maxHealth = health;
        this.health = health; // Start at full health
        this.weapon = weapon;
        this.expReward = expReward;
    }

    /**
     * Performs an attack on the target entity using the enemy's weapon.
     *
     * @param target The entity to attack
     */
    @Override
    public void attack(Entity target) {
        System.out.println(name + " attacks!");
        weapon.use(target); // Delegate attack to equipped weapon
    }

    /**
     * Reduces the enemy's health by the specified amount.
     *
     * @param amount The amount of damage to take
     */
    @Override
    public void takeDamage(int amount) {
        health -= amount;
        System.out.printf("%s took %d damage (%d/%d HP)%n",
                name, amount, health, maxHealth);
    }

    /**
     * Restores health points to the enemy.
     *
     * @param amount The amount of health to restore
     */
    @Override
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth); // Prevent overhealing
        System.out.println(name + " recovered " + amount + " HP!");
    }

    /**
     * Abstract method for enemy descriptions.
     * Must be implemented by concrete enemy classes.
     *
     * @return A formatted description of the enemy
     */
    @Override
    public abstract String getDescription();

    // ========== GETTER METHODS ========== //

    /**
     * @return The enemy's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Current health points
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * @return Maximum health capacity
     */
    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @return Experience points awarded when defeated
     */
    public int getExpReward() {
        return expReward;
    }

    /**
     * Checks if the enemy is still alive.
     *
     * @return true if health > 0, false otherwise
     */
    @Override
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Default defend action for enemies.
     * Can be overridden by specific enemy types.
     */
    @Override
    public void defend() {
        System.out.println(name + " braces for impact!");
    }
}