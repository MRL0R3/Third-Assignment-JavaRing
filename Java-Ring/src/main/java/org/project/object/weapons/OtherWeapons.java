package org.project.object.weapons;

import java.util.List;

import org.project.entity.Entity;

public class OtherWeapons {
    // Made classes public and non-static since they're in their own file
    // Each weapon is now a standalone class extending Weapon

    public static class FireSword extends Weapon {
        private static final int BURN_DURATION = 3;
        private static final int BURN_DAMAGE = 5;

        public FireSword() {
            super("Flametongue Sword", 30, 10, 100);
        }

        @Override
        public int getDamage() {
            return baseDamage + (int)(Math.random() * 5); // 30-34 damage
        }

        @Override
        public void specialAbility(List<Entity> targets) {
            System.out.println("Sword erupts in flames!");
            for (Entity target : targets) {
                int damage = getDamage() + 15;
                target.takeDamage(damage);
                System.out.printf("%s takes %d fire damage!%n", target.getName(), damage);
                applyBurn(target);
            }
        }

        private void applyBurn(Entity target) {
            System.out.printf("%s catches fire for %d turns!%n", 
                target.getName(), BURN_DURATION);
            // Implementation for burn over time
            new Thread(() -> {
                try {
                    for (int i = 0; i < BURN_DURATION && target.isAlive(); i++) {
                        Thread.sleep(2000); // Simulate turn delay
                        target.takeDamage(BURN_DAMAGE);
                        System.out.printf("%s takes %d burn damage!%n",
                            target.getName(), BURN_DAMAGE);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    public static class FrostAxe extends Weapon {
        private static final double FREEZE_CHANCE = 0.2;
        private static final int FREEZE_DURATION = 2;

        public FrostAxe() {
            super("Frostbite Axe", 35, 15, 80);
        }

        @Override
        public void specialAbility(List<Entity> targets) {
            System.out.println("Axe releases a chilling wave!");
            for (Entity target : targets) {
                target.takeDamage(20);
                if (Math.random() < FREEZE_CHANCE) {
                    System.out.println(target.getName() + " is frozen solid!");
                    // Freeze would prevent target from acting for FREEZE_DURATION turns
                }
            }
        }
    }

    public static class PoisonDagger extends Weapon {
        private static final int POISON_DURATION = 3;
        private static final int POISON_DAMAGE = 3;

        public PoisonDagger() {
            super("Venomstrike Dagger", 20, 5, 120);
        }

        @Override
        public void specialAbility(List<Entity> targets) {
            System.out.println("Dagger drips with venom!");
            for (Entity target : targets) {
                applyPoison(target);
            }
        }

        private void applyPoison(Entity target) {
            System.out.printf("%s is poisoned for %d turns!%n",
                target.getName(), POISON_DURATION);
            // Poison damage over time
            new Thread(() -> {
                try {
                    for (int i = 0; i < POISON_DURATION && target.isAlive(); i++) {
                        Thread.sleep(2000); // Simulate turn delay
                        target.takeDamage(POISON_DAMAGE);
                        System.out.printf("%s takes %d poison damage!%n",
                            target.getName(), POISON_DAMAGE);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}