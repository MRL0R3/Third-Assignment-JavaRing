package org.project.entity.players;

import org.project.entity.Entity;
import org.project.object.armors.KnightArmor;
import org.project.object.weapons.Sword;


public class Knight extends Player {
    private int kickCooldown;

    private int level;

        public int getLevel() {
            return level;
        }
    
        public void setLevel(int level) {
            this.level = level;
        }
    
    public Knight(String name) {
        super(name, 150, 50, new Sword(), new KnightArmor());
        this.kickCooldown = 0;
    }
    
    
    public void useSpecialAbility(Entity target) {
        if(kickCooldown <= 0 ) {
            int damage = 30 + (getLevel() * 5);
            System.out.println(getName() + "Launches a fierce Knight kick!");
            target.takeDamage(damage);
            kickCooldown = 3;
        }
        else{
            System.out.println("Kick ability is on recharge ("
            + kickCooldown + " turns remaining)");
        }

    }
    public void reduceCooldown(){
        if(kickCooldown > 0) {kickCooldown--;}
    }
}