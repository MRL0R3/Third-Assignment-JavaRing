package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.object.weapons.Claw;

/**
 * Dragon enemy class - A powerful fire-breathing creature with damage reduction.
 * Special abilities include fire breath attacks and burn damage over time.
 */
public class Dragon extends Enemy {
    // Combat state tracking
    private boolean usedFireBreath;          // Flag for emergency fire breath
    private static final int FIRE_BREATH_COOLDOWN = 3;  // Turns between fire breaths
    private int fireBreathCooldown = 0;      // Current cooldown counter
    private static final double BURN_CHANCE = 0.3;  // 30% chance to apply burn

    /**
     * Constructs a new Dragon enemy
     * @param health Initial health points
     * @param expReward Experience granted when defeated
     * @param claw Natural weapon (Claw) for basic attacks
     */
    public Dragon(int health, int expReward, Claw claw) {
        super("Dragon", health, new Claw(), expReward);
        this.usedFireBreath = false;
    }

    /**
     * Dragon's attack logic with fire breath conditions
     * @param target The entity being attacked
     */
    @Override
    public void attack(Entity target) {
        // Emergency fire breath when below 50% health (once per combat)
        if (!usedFireBreath && health < maxHealth / 2) {
            useSpecialAbility(target);
            usedFireBreath = true;
        }
        // Random fire breath (25% chance when off cooldown)
        else if (fireBreathCooldown <= 0 && Math.random() < 0.25) {
            useSpecialAbility(target);
            fireBreathCooldown = FIRE_BREATH_COOLDOWN;
        }
        // Standard claw attack
        else {
            super.attack(target);
            // Reduce cooldown if active
            if (fireBreathCooldown > 0) {
                fireBreathCooldown--;
            }
        }
    }

    /**
     * Dragon's special ability - Fire Breath
     * Deals heavy damage with chance to apply burn effect
     * @param target The entity being targeted
     */
    @Override
    public void useSpecialAbility(Entity target) {
        System.out.println("Dragon rears back and unleashes a torrent of flames!");

        // Calculate base damage (2.5x weapon damage)
        int baseDamage = (int) (weapon.getDamage() * 2.5);

        // Halve damage if target is defending
        int actualDamage = target.isDefending() ? baseDamage / 2 : baseDamage;

        // Apply damage
        target.takeDamage(actualDamage);
        System.out.printf("%s takes %d fire damage!%n", target.getName(), actualDamage);

        // 30% chance to apply burn effect
        if (Math.random() < BURN_CHANCE) {
            int burnDamage = (int) (baseDamage * 0.3);
            applyBurn(target, burnDamage);
        }

        // Dragon takes 5% max health as self-damage (exhaustion)
        this.takeDamage((int) (maxHealth * 0.05));
    }

    /**
     * Applies burn damage over time (3 turns)
     * @param target The burning entity
     * @param burnDamage Damage per turn
     */
    private void applyBurn(Entity target, int burnDamage) {
        System.out.printf(" %s catches fire and will take %d damage over time!%n",
                target.getName(), burnDamage * 3);
        System.out.println(target.getName() + " is burning!");

        // Burn effect in separate thread (non-blocking)
        new Thread(() -> {
            try {
                // Damage over 3 turns
                for (int i = 0; i < 3 && target.isAlive(); i++) {
                    Thread.sleep(2000); // 2 second delay between ticks
                    target.takeDamage(burnDamage);
                    System.out.println(target.getName() + " takes " + burnDamage + " burn damage!");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * Dragon takes reduced damage due to thick scales
     * @param amount Incoming damage amount
     */
    @Override
    public void takeDamage(int amount) {
        // 25% damage reduction
        int reducedDamage = (int) (amount * 0.75);
        super.takeDamage(reducedDamage);
        System.out.println("Dragon's scales reduce damage to " + reducedDamage + "!");
    }

    // --- Inherited Method Implementations ---

    @Override
    public String getName() {
        return "Dragon";
    }

    @Override
    public void gainExperience(int amount) {
        // Dragons don't gain experience
    }

    @Override
    public void healMana(int amount) {
        // Dragons don't use mana
    }

    @Override
    public int getMana() {
        return 0; // Dragons have no mana pool
    }

    @Override
    public int getMaxMana() {
        return 0; // Dragons have no mana pool
    }

    /**
     * Provides formatted description with current status
     * @return String describing dragon and its health
     */
    @Override
    public String getDescription() {
        return String.format(
                "Dragon with fiery breath (Reduces damage by 25% | Health: %d/%d)",
                health, maxHealth
        );
    }
}