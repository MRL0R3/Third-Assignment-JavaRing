package org.project.entity.players;

import java.util.ArrayList;
import java.util.List;

import org.project.entity.Entity;
import org.project.object.armors.Armor;
import org.project.object.consumables.Consumable;
import org.project.object.consumables.Flask;
import org.project.object.weapons.Weapon;

public abstract class Player implements Entity {

    protected final  String name;
    protected int health;
    protected int maxHealth;
    protected static int mana;
    protected static int maxMana;
    protected Weapon weapon;
    protected Armor armor;
    protected int level;
    protected int experience;
    protected static List<Consumable> inventory;
    protected boolean defending;

    public Player(String name, int maxHealth, int maxMana, Weapon weapon, Armor armor) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.weapon = weapon;
        this.armor = armor;
        this.level = 1;
        this.experience = 0;
        this.inventory = new ArrayList<>();
        this.inventory.add(new Flask()); // Starting item
        this.maxMana = maxMana;
        this.mana = maxMana;
    }


    public abstract void use(Entity target);

    @Override
    public void attack(Entity target) {
        weapon.use(target);
    }

    @Override
    public void defend() {
        defending = true;
        System.out.println(name + " takes defensive stance!");
    }

    @Override
    public void takeDamage(int amount) {
        int reducedDamage = armor.protect(amount);
        health -= reducedDamage;
        defending = false;

        System.out.printf("%s took %d damage (reduced from %d)%n",
                name, reducedDamage, amount);

        if (health <= 0) {
            System.out.println(name + " has been defeated!");
        }
    }

    @Override
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
        System.out.println(name + " recovered " + amount + " HP!");
    }

    @Override
    public void gainExperience(int amount) {
        experience += amount;
        System.out.println(name + " gained " + amount + " XP!");

        if (experience >= level * 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        maxHealth += 10;
        maxMana += 5;
        health = maxHealth;
        mana = maxMana;
        System.out.printf("%s leveled up to %d!%n", name, level);
    }

    // Inventory management
    public void useItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            Consumable item = inventory.get(index);
            item.use(this);
            if (item.getRemainingUses() <= 0) {
                inventory.remove(index);
            }
        }
    }

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

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public List<Consumable> getInventory() {
        return inventory;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public boolean isDefending() {
        return defending;
    }

    @Override
    public void healMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
        System.out.println(name + " recovered " + amount + " MP!");
    }
}
