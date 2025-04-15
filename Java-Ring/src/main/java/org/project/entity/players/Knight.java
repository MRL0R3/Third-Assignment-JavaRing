package org.project.entity.players;

import org.project.entity.Entity;
import org.project.object.armors.KnightArmor;
import org.project.object.weapons.Sword;

/**
 * The Knight player class - A heavily armored warrior specializing in melee combat
 * and defensive abilities. Features a powerful kick special ability with cooldown.
 */
public class Knight extends Player {

    // Combat cooldown tracking
    private int kickCooldown;               // Turns remaining until kick can be used again
    private static final int KICK_DAMAGE = 35; // Base damage for the kick ability

    /**
     * Constructs a new Knight character with default equipment
     * @param name The knight's display name
     */
    public Knight(String name) {
        super(name, 200, 30, new Sword(), new KnightArmor()); // High health, medium mana
        this.kickCooldown = 0; // Ability starts ready
    }

    /**
     * Checks if the knight's kick ability is available
     * @return true if kick can be used, false if on cooldown
     */
    public boolean hasKickReady() {
        return kickCooldown <= 0;
    }

    /**
     * Knight's special ability - Mighty Kick
     * Deals heavy damage with scaling based on level
     * @param target The entity to attack
     */
    @Override
    public void useSpecialAbility(Entity target) {
        if (hasKickReady()) {
            // Calculate damage: base + (2 * level)
            int totalDamage = KICK_DAMAGE + (level * 2);
            System.out.printf("%s performs a mighty kick (%d damage)!%n",
                    name, totalDamage);
            target.takeDamage(totalDamage);
            kickCooldown = 3; // 3-turn cooldown
        } else {
            System.out.printf("Kick on cooldown (%d turns remaining)%n",
                    kickCooldown);
        }
    }

    /**
     * Reduces all active cooldowns by 1 turn
     * Called automatically after each attack
     */
    public void reduceCooldowns() {
        if (kickCooldown > 0) kickCooldown--;
    }

    /**
     * Performs a standard attack and reduces cooldowns
     * @param target The entity to attack
     */
    @Override
    public void attack(Entity target) {
        super.attack(target); // Perform basic weapon attack
        reduceCooldowns();    // Progress ability cooldowns
    }

    /**
     * Generates a character status description
     * @return Formatted string with health and ability status
     */
    @Override
    public String getDescription() {
        return String.format("%s - Lvl %d Knight (%d/%d HP) | %s",
                name, level, health, maxHealth,
                hasKickReady() ? "Kick Ready" : "Kick CD: " + kickCooldown);
    }

    // ====== Inherited Method Implementations ====== //

    /**
     * @return The knight's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Placeholder for item usage functionality
     * @param target The target entity (unused)
     */
    @Override
    public void use(Entity target) {
        // TODO: Implement knight-specific item interactions
    }
}