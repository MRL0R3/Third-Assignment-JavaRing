package org.project.object.weapons;

import java.util.ArrayList;

import org.project.entity.Entity;


public class Sword extends Weapon {

    int abilityCharge;

    public Sword() {
        super(20, 10);
        this.abilityCharge = 0;
    }


    public void fireWhirlAttack(ArrayList<Entity> targets) {
        abilityCharge += 2;
        if(abilityCharge >= 3){
            for(Entity target : targets) {
                target.takeDamage(getDamage() * 2);
            }
            abilityCharge = 0;
            System.out.println("Firewhirl attack!");
        }
        else {
            System.out.println("Ability is not charged yet!");
        }
    }
    public void chargingAbility(){
        abilityCharge++;
    }
}
