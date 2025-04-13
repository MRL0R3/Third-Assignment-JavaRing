package org.project.object.weapons;

import org.project.entity.Entity;
import java.util.List;

public class RustySword extends Weapon {
    private static final int BASE_DAMAGE = 18;
    private static final double RUST_CHANCE = 0.3; // 30% chance to apply rust effect
    private static final int RUST_DAMAGE_PENALTY = 2; // Reduces target's damage

    public RustySword() {
        super("Rusty Sword", BASE_DAMAGE, 8, 40); // name, damage, manaCost, durability
    }

    @Override
    public int getDamage() {
        // 5% chance to break on hit
        if (Math.random() < 0.05) {
            durability -= 5;
        }
        return BASE_DAMAGE;
    }

    @Override
    public void specialAbility(List<Entity> targets) {
        if (targets.isEmpty()) return;

        Entity target = targets.get(0);
        if (Math.random() < RUST_CHANCE) {
            System.out.printf("%s's rust infects %s, reducing their damage output!%n",
                    name, target.getName());
            // Implementation would reduce target's damage by RUST_DAMAGE_PENALTY
        }
    }

    @Override
    public String getDescription() {
        return String.format(
                "%s (Damage: %d, Rust: %.0f%%, Durability: %d/%d)",
                name, BASE_DAMAGE, RUST_CHANCE * 100, durability, maxDurability
        );
    }

    // Unique rust effect application
    public void applyRust(Entity target) {
        // Implementation would track damage reduction on target
        System.out.println("Rust spreads, weakening " + target.getName());
    }
}