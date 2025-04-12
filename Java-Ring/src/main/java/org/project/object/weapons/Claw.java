package org.project.object.weapons;

import java.util.List;

import org.project.entity.Entity;

public class Claw extends Weapon {
    public Claw() {
        super("Dragon Claws", 25, 0, Integer.MAX_VALUE);
    }
    
    @Override
    public int getDamage() {
        return 25 + (int)(Math.random() * 5); // 25-30 damage
    }
    
    @Override 
    public void specialAbility(List<Entity> targets) {
        System.out.println("Dragon performs a rending slash!");
        for (Entity target : targets) {
            target.takeDamage(getDamage() * 3 / 4); // 75% damage to all
        }
    }
}