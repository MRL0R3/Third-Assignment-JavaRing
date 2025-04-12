package org.project.entity.players;

import org.project.entity.Entity;
import org.project.object.armors.Armor;
import org.project.object.weapons.Weapon;

// TODO: UPDATE IMPLEMENTATION(Updated)
public abstract class Player implements Entity {

    protected String name;
    Weapon weapon;
    Armor armor;
    private int hp;
    private int maxHP;
    private int mp;
    private int maxMP;
    private int experience = 0;
    private int level = 1;
    private boolean isDefending = false;
    
    public Player(String name, int hp, int mp, Weapon weapon, Armor armor) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;

        this.weapon = weapon;
        this.armor = armor;
    }

    public void gainExperience(int amount) {
        experience += amount;
        System.out.println("Gained " + amount + " XP!");

        if (experience >= level * 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        maxHP += 10;
        hp = maxHP;
        System.out.println("Level up! Now level " + level);
    }

    @Override
    public void attack(Entity target) {
        target.takeDamage(weapon.getDamage());
    }


    @Override
    public void defend() {
        isDefending = true;
        System.out.println(getName() + " raises their guard!");
    }
    @Override
    public void takeDamage(int damage) {
        int finalDamage = isDefending ? 
            Math.max(1, damage/2 - armor.getDefense()) : 
            Math.max(1, damage - armor.getDefense());
        
        hp -= finalDamage;
        isDefending = false;
        System.out.println(getName() + " takes " + finalDamage + " damage!");
    }

    @Override
    public void heal(int health) {
        hp += health;
        if (hp > maxHP) {
            hp = maxHP;
        }
    }

    @Override
    public void fillMana(int mana) {
        mp += mana;
        if (mp > maxMP) {
            mp = maxMP;
        }
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public int getMaxHP() {
        return maxHP;
    }

    public int getMp() {
        return mp;
    }

    @Override
    public int getMaxMP() {
        return maxMP;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }

}
