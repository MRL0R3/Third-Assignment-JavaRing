package org.project.object.weapons;

import java.util.List;

import org.project.entity.Entity;


public class Sword extends Weapon {

    int abilityCharge;

    public Sword() {
        super("Stell Sword" , 25 , 15, 100);
        this.abilityCharge = 0;
    }


  
    @Override
    public void specialAbility(List<Entity> targets) {
        if (abilityCharge >= 3) {
            System.out.println("Executing Whirlwind Slash!");
            for (Entity target : targets) {
                target.takeDamage(baseDamage * 2);
            }
            abilityCharge = 0;
        } else {
            System.out.printf("Need %d more attacks to charge!\n", 3 - abilityCharge);
        }
    }
    @Override
    public void use(Entity target) {
        super.use(target);
        gainCharge();
    }
    private void gainCharge() {
        if (abilityCharge < 3) {
            abilityCharge++;
        }
    }
    
}
