package org.project.object;

import org.project.entity.Entity;

public interface Object {
    // Core method all objects must implement
    void use(Entity target);
    
    // Identification methods
    String getName();
    String getDescription();
    
    // Inventory management helpers
    boolean isConsumable(); 
    int getValue(); // Gold value
    
    // Equipment status methods (default implementations)
    default boolean isBroken() { return false; }
    default void repair() {} 
}