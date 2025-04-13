package org.project.entity.players;
import org.project.entity.Entity;
import org.project.object.armors.KnightArmor;
import org.project.object.weapons.Sword;

public class Knight extends Player {

    @Override
    public String getName() {
        return this.name;
    }
    public boolean hasKickReady() {
        return kickCooldown <= 0;
    }
    @Override
    public String getDescription() {
        return String.format("%s - Lvl %d Knight (%d/%d HP) | %s",
                name, level, health, maxHealth,
                hasKickReady() ? "Kick Ready" : "Kick CD: " + kickCooldown);
    }

    private int kickCooldown;
    private static final int KICK_DAMAGE = 35;

    public Knight(String name) {
        super(name, 200, 30, new Sword(), new KnightArmor());
        this.kickCooldown = 0;
    }

    @Override
    public void useSpecialAbility(Entity target) {
        if (kickCooldown <= 0) {
            int totalDamage = KICK_DAMAGE + (level * 2);
            System.out.printf("%s performs a mighty kick (%d damage)!%n", 
                name, totalDamage);
            target.takeDamage(totalDamage);
            kickCooldown = 3;
        } else {
            System.out.printf("Kick on cooldown (%d turns remaining)%n", 
                kickCooldown);
        }
    }

    public void reduceCooldowns() {
        if (kickCooldown > 0) kickCooldown--;
    }

    @Override
    public void use(Entity target) {

    }

    @Override
    public void attack(Entity target) {
        super.attack(target);
        reduceCooldowns();
    }
}