package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.entity.enemies.Enemy;

public class Goblin extends Enemy {
    private static final double DODGE_CHANCE = 0.25;
    private static final int STEAL_AMOUNT = 5;
    private boolean hasStolen = false;

    public Goblin(int health, int expReward) {
        super("Goblin", health, new Dagger(), expReward);
    }
    
    @Override
    public void gainExperience(int amount) {
        // Enemies don't gain experience
    }

    @Override
    public void takeDamage(int amount) {
        if (Math.random() < DODGE_CHANCE) {
            System.out.println("Goblin nimbly dodges the attack!");
            return;
        }
        super.takeDamage(amount);  
    }
    @Override
    public String getName() {
        return "Goblin";
    }
    @Override
    public void useSpecialAbility(Entity target) {
        if (!hasStolen) {
            // Goblin's dirty trick: steals HP on first special use
            int damage = STEAL_AMOUNT + (int) (Math.random() * 5);
            target.takeDamage(damage);
            this.heal(damage);
            System.out.printf("Goblin stabs %s and steals %d HP!%n",
                    target.getName(), damage);
            hasStolen = true;
        } else {
            // Regular attack if already used special
            System.out.println("Goblin attempts another dirty trick but fails!");
            super.attack(target);
        }
    }

    @Override
    public void attack(Entity target) {
        // 10% chance to use special ability randomly
        if (Math.random() < 0.1) {
            useSpecialAbility(target);
        } else {
            super.attack(target);
        }
    }
    @Override
    public String getDescription() {
        return "A sneaky goblin that fights dirty (Dodge chance: 25%)";
    }
}
