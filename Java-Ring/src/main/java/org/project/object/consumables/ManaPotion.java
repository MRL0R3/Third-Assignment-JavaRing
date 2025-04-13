package org.project.object.consumables;

import org.project.entity.Entity;
import org.project.object.consumables.Consumable;

public class ManaPotion extends Consumable {
    private static final int MANA_RESTORE = 40;

    public ManaPotion() {
        super("Mana Potion", "Restores 40 MP", 75, 2);
    }

    @Override
    public void use(Entity target) {
        if (getRemainingUses() > 0) {
            target.healMana(MANA_RESTORE);
            consumeUse();
            System.out.printf("%s restored %d MP! (%d uses remain)%n",
                    target.getName(), MANA_RESTORE, getRemainingUses());
        }
    }
}