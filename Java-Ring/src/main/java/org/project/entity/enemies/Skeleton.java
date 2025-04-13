package org.project.entity.enemies;

import org.project.entity.Entity;
import org.project.object.weapons.RustySword;

public class Skeleton extends Enemy {
    private boolean hasResurrected;

    public Skeleton(int health, int expReward, RustySword rustySword) {
        super("Skeleton", health, new RustySword(), expReward);
        this.hasResurrected = false;
    }

    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount);
        if (!isAlive() && !hasResurrected) {
            resurrect();
        }
    }

    @Override
    public String getDescription() {
        return "A reanimated skeleton with a rusty sword. (Resurrects once)";
    }

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public void useSpecialAbility(Entity target) {
        System.out.println("Sneaky Skeleton doesn't have a super power" +
                " but this bastard got two lives ;) ");
    }

    @Override
    public void gainExperience(int amount) {
//        Enemies don't gain experience
    }

    @Override
    public boolean isDefending() {
        return super.isDefending();
    }

    @Override
    public void healMana(int amount) {

    }

    @Override
    public int getMana() {
        return super.getMana();
    }

    private void resurrect() {
        health = maxHealth / 2;
        hasResurrected = true;
        System.out.println("The skeleton reassembles itself!");
    }

}