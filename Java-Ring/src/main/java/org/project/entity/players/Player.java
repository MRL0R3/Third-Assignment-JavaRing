package org.project.entity.players;

import java.util.ArrayList;
import java.util.List;
import org.project.entity.Entity;
import org.project.object.armors.Armor;
import org.project.object.consumables.Consumable;
import org.project.object.consumables.Flask;
import org.project.object.weapons.Weapon;

/**
 * Abstract base class representing a player character in the game.
 * Contains core player systems including combat, inventory, and progression.
 */
public abstract class Player implements Entity {

    // Character Attributes
    protected final String name;         // Player's name
    protected int health;                // Current health points
    protected int maxHealth;             // Maximum health capacity
    protected static int mana;           // Current mana points
    protected static int maxMana;        // Maximum mana capacity
    protected Weapon weapon;             // Equipped weapon
    protected Armor armor;               // Equipped armor
    protected int level;                 // Current character level
    protected int experience;            // Accumulated experience
    protected static List<Consumable> inventory; // Item inventory
    protected boolean defending;         // Defense state flag

    /**
     * Constructs a new Player character with starting equipment.
     *
     * @param name       Character name
     * @param maxHealth  Starting maximum health
     * @param maxMana    Starting maximum mana
     * @param weapon     Starting weapon
     * @param armor      Starting armor
     */
    public Player(String name, int maxHealth, int maxMana, Weapon weapon, Armor armor) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth; // Start at full health
        this.maxMana = maxMana;
        this.mana = maxMana;     // Start at full mana
        this.weapon = weapon;
        this.armor = armor;
        this.level = 1;          // Start at level 1
        this.experience = 0;     // Start with 0 XP
        this.inventory = new ArrayList<>();
        this.inventory.add(new Flask()); // Give starting health potion
        this.defending = false;  // Not defending initially
    }

    // ========== COMBAT METHODS ========== //

    /**
     * Abstract method for class-specific special abilities.
     * Must be implemented by concrete player classes.
     *
     * @param target The entity to use the ability on
     */
    public abstract void use(Entity target);

    /**
     * Performs a basic attack using the equipped weapon.
     *
     * @param target The entity to attack
     */
    @Override
    public void attack(Entity target) {
        weapon.use(target); // Delegate attack to weapon
    }

    /**
     * Enters a defensive stance, reducing incoming damage.
     */
    @Override
    public void defend() {
        defending = true;
        System.out.println(name + " takes defensive stance!");
    }

    /**
     * Takes damage after armor mitigation.
     *
     * @param amount Raw damage amount before reduction
     */
    @Override
    public void takeDamage(int amount) {
        int reducedDamage = armor.protect(amount); // Apply armor reduction
        health -= reducedDamage;
        defending = false; // Defense ends after being hit

        System.out.printf("%s took %d damage (reduced from %d)%n",
                name, reducedDamage, amount);

        if (health <= 0) {
            System.out.println(name + " has been defeated!");
        }
    }

    // ========== HEALTH/MANA MANAGEMENT ========== //

    /**
     * Restores health points.
     *
     * @param amount Amount to heal (won't exceed maxHealth)
     */
    @Override
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
        System.out.println(name + " recovered " + amount + " HP!");
    }

    /**
     * Restores mana points.
     *
     * @param amount Amount to restore (won't exceed maxMana)
     */
    @Override
    public void healMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
        System.out.println(name + " recovered " + amount + " MP!");
    }

    // ========== PROGRESSION SYSTEM ========== //

    /**
     * Awards experience points and handles level-ups.
     *
     * @param amount Experience points to add
     */
    @Override
    public void gainExperience(int amount) {
        experience += amount;
        System.out.println(name + " gained " + amount + " XP!");

        // Level up if enough experience (100 XP per level)
        if (experience >= level * 100) {
            levelUp();
        }
    }

    /**
     * Increases character level and improves stats.
     */
    private void levelUp() {
        level++;
        maxHealth += 10; // HP increase per level
        maxMana += 5;    // MP increase per level
        health = maxHealth; // Fully heal on level up
        mana = maxMana;     // Fully restore mana
        System.out.printf("%s leveled up to %d!%n", name, level);
    }

    // ========== INVENTORY SYSTEM ========== //

    /**
     * Uses an item from inventory.
     *
     * @param index Inventory slot number (0-based)
     */
    public void useItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            Consumable item = inventory.get(index);
            item.use(this); // Apply item effect

            // Remove if depleted
            if (item.getRemainingUses() <= 0) {
                inventory.remove(index);
            }
        }
    }

    // ========== GETTER METHODS ========== //

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public List<Consumable> getInventory() {
        return inventory;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public boolean isDefending() {
        return defending;
    }
}