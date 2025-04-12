package org.project.entity;

public interface Entity {
    // Combat Core
    void attack(Entity target);
    void takeDamage(int damage);
    void heal(int health);



    // Status methods
    boolean isAlive();
    String getname();
    int getHealth();
    int getMaxHealth();


    // Magic/Ability system
    void useSpecialAbility(Entity target);
    void defend();
    
    String getName();  

    // Resource management
    void gainExperience(int amount);
    default boolean isDefending() { return false; }


    
    String getDescription();
}
