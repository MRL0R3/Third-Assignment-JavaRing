package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.entity.enemies.Enemy;
import org.project.object.weapons.Claw;

public class Dragon extends Enemy {

    private boolean usedFireBreath;
    private static final int FIRE_BREATH_COOLDOWN = 3;
    private int fireBreathCooldown = 0;
    private static final double BURN_CHANCE = 0.3;

    public Dragon(int health, int expReward) {
        super("Ancient Dragon", health, new Claw(), expReward);
        this.usedFireBreath = false;
    }


    @Override
    public void attack(Entity target) {
        if (!usedFireBreath && health < maxHealth / 2) {
            fireBreath(target);
            usedFireBreath = true;
        } else if (fireBreathCooldown <= 0 && Math.random() < 0.25) {
            fireBreath(target);
            fireBreathCooldown = FIRE_BREATH_COOLDOWN;
        } else {
            super.attack(target);
            if (fireBreathCooldown > 0) {
                fireBreathCooldown--;
            }
        }
    }

    

    private void fireBreath(Entity target) {
        System.out.println("Dragon rears back and unleashes a torrent of flames!");
        int baseDamage = (int) (weapon.getDamage() * 2.5);
        int actualDamage = target.isDefending() ? baseDamage / 2 : baseDamage;

        target.takeDamage(actualDamage);
        System.out.printf("%s takes %d fire damage!%n", target.getName(), actualDamage);

        if (Math.random() < BURN_CHANCE) {
            int burnDamage = (int) (baseDamage * 0.3);
            applyBurn(target, burnDamage);
        }
        this.takeDamage((int) (maxHealth * 0.05));
    }

    private void applyBurn(Entity target, int burnDamage) {
        System.out.printf(" %s catches fire and will take %d damage over time!%n",
                target.getName(), burnDamage * 3);
        System.out.println(target.getName() + " is burning!");
        new Thread(() -> {
            try {
                // Burn over 3 turns
                for (int i = 0; i < 3 && target.isAlive(); i++) {
                    Thread.sleep(2000); // Simulate turn delay
                    target.takeDamage(burnDamage);
                    System.out.println(target.getName() + " takes " + burnDamage + " burn damage!");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @Override
    public void takeDamage(int amount) {
        // Dragons take reduced damage from non-magical attacks
        int reducedDamage = (int) (amount * 0.75);
        super.takeDamage(reducedDamage);
        System.out.println("Dragon's scales reduce damage to " + reducedDamage + "!");
    }
    @Override
    public String getDescription() {
        return String.format(
            "Ancient dragon with fiery breath (Reduces damage by 25% | Health: %d/%d)",
            health, maxHealth
        );
    }
}