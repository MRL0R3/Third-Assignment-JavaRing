package org.project.object.weapons;

import org.project.entity.Entity;
import java.util.List;

public class Dagger extends Weapon {
    private static final int BASE_DAMAGE = 15;
    private static final int CRIT_CHANCE = 25; // 25% crit chance
    private static final int CRIT_MULTIPLIER = 2;

    public Dagger() {
        super("Rusty Dagger", BASE_DAMAGE, 5, 50); // name, damage, manaCost, durability
    }

    @Override
    public int getDamage() {
        // 25% chance to crit for double damage
        return (Math.random() * 100 < CRIT_CHANCE) ?
                BASE_DAMAGE * CRIT_MULTIPLIER :
                BASE_DAMAGE;
    }

    @Override
    public void specialAbility(List<Entity> targets) {
        if (targets.isEmpty()) return;

        Entity primaryTarget = targets.get(0);
        int damage = getDamage() + 5; // Backstab bonus

        System.out.printf("Goblin backstabs %s for %d damage!%n",
                primaryTarget.getName(), damage);
        primaryTarget.takeDamage(damage);
    }

    @Override
    public String getDescription() {
        return String.format(
                "%s (Damage: %d, Crit: %d%%, Durability: %d/%d)",
                name, BASE_DAMAGE, CRIT_CHANCE, durability, maxDurability
        );
    }
}