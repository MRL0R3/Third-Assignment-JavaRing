package org.project.entity;

public interface Entity {
    // Combat Core
    void attack(Entity target);
    void takeDamage(int damage);
    void heal(int health);



    // Status methods
    boolean isAlive();

    int getHealth();
    int getMaxHealth();


    // Magic/Ability system
    void useSpecialAbility(Entity target);
    void defend();
    
    String getName();  

    // Resource management
    void gainExperience(int amount);
    default boolean isDefending() { return false; }
    void healMana(int amount);
    default int getMana() { return 0; }
    default int getMaxMana() { return 0; }

    
    String getDescription();
}
