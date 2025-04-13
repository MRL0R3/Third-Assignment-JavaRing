package org.project.object.weapons;

import org.project.entity.Entity;

import java.util.List;

public class Staff extends Weapon {
    public Staff() {
        super("Wizard's Staff", 12, 0, 60); // Low physical damage, no mana cost
    }

    @Override
    public void specialAbility(List<Entity> targets) {

    }

    @Override
    public String getDescription() {
        return name + " (Damage: " + baseDamage + ", +5 mana regen when equipped)";
    }
}