package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.object.weapons.Weapon;

public abstract class Enemy implements Entity {

    protected final String name; 
    protected int health;
    protected int maxHealth;
    protected Weapon weapon;
    protected int expReward;

    public Enemy(String name, int health, Weapon weapon, int expReward) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.weapon = weapon;
        this.expReward = expReward;
    }

    @Override
    public void attack(Entity target) {
        System.out.println(name + " attacks!");
        weapon.use(target);
    }

    @Override
    public void takeDamage(int amount) {
        health -= amount;
        System.out.printf("%s took %d damage (%d/%d HP)%n",
                name, amount, health, maxHealth);
    }

    @Override
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
        System.out.println(name + " recovered " + amount + " HP!");
    }
    
    @Override
    public abstract String getDescription();
    
    // Getters
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getExpReward() {
        return expReward;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void defend() {
        System.out.println(name + " braces for impact!");
    }

}
