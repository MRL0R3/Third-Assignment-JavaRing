package org.project.object.consumables;

import org.project.entity.Entity;

public class Flask extends Consumable {
    private static final int HEAL_POWER = 30;
    private static final String NAME = "Healing Flask";
    private static final String DESCRIPTION = "Restores 30 HP";
    private static final int VALUE = 50;
    private static final int MAX_USES = 3;

    public Flask() {
        super(NAME, DESCRIPTION, VALUE, MAX_USES);
    }

    @Override
    public void use(Entity target) {
        if (getRemainingUses() > 0) {
            target.heal(HEAL_POWER);
            consumeUse();
            System.out.printf("%s healed for %d HP! (%d uses remain)%n",
                target.getName(), HEAL_POWER, getRemainingUses());
            
            // Bonus: Flask gets more effective at low health
            if (target.getHealth() < target.getMaxHealth() * 0.3) {
                int bonusHeal = HEAL_POWER / 2;
                target.heal(bonusHeal);
                System.out.printf("Emergency boost! Additional %d HP restored!%n", bonusHeal);
            }
        } else {
            System.out.println("Flask is empty!");
        }
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    // Bonus: Refill method
    public void refill() {
        if (getRemainingUses() < MAX_USES) {
            System.out.println("Flask has been refilled!");
            resetUses();
        } else {
            System.out.println("Flask is already full!");
        }
    }

    // Method to reset the uses of the flask
    private void resetUses() {
        setRemainingUses(MAX_USES);
    }

    // Setter for remaining uses
    private void setRemainingUses(int uses) {
        this.remainingUses = uses;
    }
}