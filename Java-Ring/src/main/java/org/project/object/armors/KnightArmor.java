package org.project.object.armors;

import org.project.entity.Entity;
import org.project.object.Object;

public class KnightArmor extends Armor implements Object {
    private static final int BASE_DEFENSE = 25;
    private static final int BASE_DURABILITY = 150;
    private static final String ARMOR_NAME = "Knight's Plate Armor";

    public KnightArmor() {
        super(ARMOR_NAME, BASE_DEFENSE, BASE_DURABILITY);
    }

    @Override
    public void use(Entity target) {
        // Armor can't be "used" like consumables, but we implement the required method
        System.out.println(ARMOR_NAME + " cannot be used directly");
    }

    @Override
    public int protect(int incomingDamage) {
        // Knight armor has 15% chance to completely block physical attacks
        if (Math.random() < 0.15 && getDurability() > 0) {
            System.out.println(ARMOR_NAME + " deflects the attack completely!");
            reduceDurability(); // Blocking still stresses the armor
            return 0;
        }
        return super.protect(incomingDamage);
    }

    private void reduceDurability() {
        if (durability > 0) {
            durability -= 2;
        }
    }

    @Override
    public void repair() {
        super.repair();
        System.out.println(ARMOR_NAME + " shines like new after repair!");
    }

    @Override
    public String getDescription() {
        return String.format("%s (Def: %d, Dur: %d/%d) - Heavy plate armor with chance to block attacks",
                name, getBaseDefense(), getDurability(), maxDurability);
    }

    public int getBaseDefense() {
        return BASE_DEFENSE;
    }

    @Override
    public boolean isConsumable() {
        return false;
    }
}