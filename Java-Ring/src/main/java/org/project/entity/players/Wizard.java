package org.project.entity.players;

import org.project.entity.Entity;
import org.project.object.armors.Armor;
import org.project.object.consumables.ManaPotion;
import org.project.object.weapons.Staff;

public class Wizard extends Player {
    private int manaRegenRate;
    private static final int BASE_MANA_REGEN = 5;
    private static final int FIREBALL_COST = 20;
    private static final int FIREBALL_DAMAGE = 40;

    public Wizard(String name) {
        super(name, 120, 100, new Staff(), new RobeArmor());
        this.manaRegenRate = BASE_MANA_REGEN;
        this.inventory.add(new ManaPotion()); // Starting mana potion
    }

    @Override
    public void use(Entity target) {
        if (target == null) {
            System.out.println(name + " waves their staff but nothing happens!");
            return;
        }

        // Check if using a mana potion when mana is low
        if (mana < maxMana * 0.3 && hasManaPotion()) {
            useManaPotion();
            return;
        }

        // Default spell casting behavior
        if (mana >= FIREBALL_COST) {
            castFireball(target);
        } else if (mana >= 10) {
            castMagicMissile(target);
        } else {
            System.out.println(name + " is too exhausted to cast spells!");
            basicStaffAttack(target);
        }
    }

    @Override
    public void useSpecialAbility(Entity target) {
        if (mana >= FIREBALL_COST) {
            castFireball(target);
        } else {
            System.out.printf("%s doesn't have enough mana! (Need %d, has %d)%n",
                    name, FIREBALL_COST, mana);
        }
    }

    @Override
    public void attack(Entity target) {
        if (Math.random() < 0.3 && mana >= 10) { // 30% chance to use magic missile
            castMagicMissile(target);
        } else {
            basicStaffAttack(target);
        }
        regenerateMana();
    }

    private void castFireball(Entity target) {
        System.out.printf("%s hurls a massive Fireball at %s! (%d damage)%n",
                name, target.getName(), FIREBALL_DAMAGE);
        target.takeDamage(FIREBALL_DAMAGE);
        mana -= FIREBALL_COST;

        // 20% chance to apply burn effect
        if (Math.random() < 0.2) {
            applyBurnEffect(target, FIREBALL_DAMAGE / 4);
        }
    }

    private void castMagicMissile(Entity target) {
        int damage = 15 + (int)(Math.random() * 10);
        System.out.printf("%s shoots Magic Missiles at %s! (%d damage)%n",
                name, target.getName(), damage);
        target.takeDamage(damage);
        mana -= 10;
    }

    private void basicStaffAttack(Entity target) {
        System.out.printf("%s bonks %s with their staff!%n", name, target.getName());
        super.attack(target); // Uses the default weapon attack
    }

    private void regenerateMana() {
        mana = Math.min(maxMana, mana + manaRegenRate);
    }

    private boolean hasManaPotion() {
        return inventory.stream()
                .anyMatch(item -> item instanceof ManaPotion && item.getRemainingUses() > 0);
    }

    private void useManaPotion() {
        inventory.stream()
                .filter(item -> item instanceof ManaPotion)
                .findFirst()
                .ifPresent(potion -> {
                    System.out.println(name + " quickly drinks a mana potion!");
                    potion.use(this);
                });
    }

    private void applyBurnEffect(Entity target, int burnDamage) {
        System.out.printf("%s catches fire! (%d burn damage over 3 turns)%n",
                target.getName(), burnDamage * 3);

        // Simple turn-based burn effect
        new Thread(() -> {
            try {
                for (int i = 0; i < 3 && target.isAlive(); i++) {
                    Thread.sleep(1500); // Simulate turn delay
                    target.takeDamage(burnDamage);
                    System.out.println(target.getName() + " takes " + burnDamage + " burn damage!");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @Override
    public String getDescription() {
        return String.format("%s - Lvl %d Wizard (%d/%d HP, %d/%d MP)",
                name, level, health, maxHealth, mana, maxMana);
    }

    // Wizard-specific armor
    private static class RobeArmor extends Armor {
        public RobeArmor() {
            super("Wizard's Robe", 10, 80);
        }

        @Override
        public int protect(int incomingDamage) {
            // Robes reduce magic damage more than physical
            int reducedDamage = (int)(incomingDamage * 0.8); // 20% reduction
            durability -= 1; // Robes degrade slower
            return reducedDamage;
        }

        @Override
        public void use(Entity target) {

        }
    }
}