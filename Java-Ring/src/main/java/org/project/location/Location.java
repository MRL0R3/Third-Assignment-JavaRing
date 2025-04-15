package org.project.location;

import java.util.Random;
import org.project.entity.enemies.*;
import org.project.object.weapons.*;

/**
 * Represents a game location with biome-specific properties and enemy spawning logic.
 * Handles danger level progression and location-based enemy generation.
 */
public class Location {

    /**
     * Biome types that affect enemy spawn rates and location properties
     */
    public enum Biome {
        FOREST,   // Dense woodland areas
        CAVE,     // Underground caverns
        MOUNTAIN, // High altitude terrain
        RUINS     // Ancient structures
    }

    // Location properties
    private String name;            // Location display name
    private String description;     // Flavor text description
    private int dangerLevel;        // Difficulty scaling factor (1+)
    private Random random = new Random(); // Random number generator
    private Biome biome;            // Biome type

    /**
     * Constructs a new game location
     * @param name        The location's name
     * @param description Descriptive text about the location
     * @param biome       The biome type (FOREST, CAVE, etc.)
     */
    public Location(String name, String description, Biome biome) {
        this.name = name;
        this.description = description;
        this.dangerLevel = 1; // Start at minimum danger
        this.biome = biome;
    }

    /**
     * Generates an enemy scaled to current danger level
     * @return A new Enemy instance (Goblin/Skeleton/Dragon)
     * @throws IllegalStateException if invalid enemy type generated
     */
    public Enemy generateEnemy() {
        try {
            // Scale stats based on danger level
            int scaledHealth = 50 + (dangerLevel * 20);
            int scaledExp = 30 + (dangerLevel * 10);

            // Randomly select enemy type (0-2)
            int enemyType = random.nextInt(3);

            System.out.println("Generating enemy type: " + enemyType); // Debug output

            return switch (enemyType) {
                case 0 -> new Goblin(scaledHealth, scaledExp, new Dagger());
                case 1 -> new Skeleton(scaledHealth, scaledExp, new RustySword());
                case 2 -> new Dragon(scaledHealth, scaledExp, new Claw());
                default -> throw new IllegalStateException("Invalid enemy type");
            };
        } catch (Exception e) {
            System.err.println("Failed to generate enemy: " + e.getMessage());
            return null; // Fallback for error cases
        }
    }

    /**
     * Gets biome-specific enemy spawn weights
     * @return Array of weights [Goblin%, Skeleton%, Dragon%]
     */
    private double[] getBiomeWeights() {
        return switch (biome) {
            case FOREST -> new double[]{70, 25, 5};   // Mostly Goblins
            case CAVE -> new double[]{30, 65, 5};     // Skeleton-heavy
            case MOUNTAIN -> new double[]{10, 30, 60}; // Dragon territory
            case RUINS -> new double[]{40, 55, 5};    // Undead-focused
            default -> new double[]{60, 35, 5};       // Default mix
        };
    }

    /**
     * Increases location danger level
     * Makes future enemies stronger and increases rewards
     */
    public void increaseDanger() {
        dangerLevel++;
        System.out.println(name + " grows more dangerous...");
    }

    // ========== GETTER METHODS ========== //

    /**
     * @return Location name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Location description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Current danger level (1+)
     */
    public int getDangerLevel() {
        return dangerLevel;
    }

    /**
     * Gets descriptive danger rating
     * @return "Safe", "Risky", or "Deadly" based on danger level
     */
    public String getDangerDescription() {
        if (dangerLevel <= 2) return "Safe";
        if (dangerLevel <= 5) return "Risky";
        return "Deadly";
    }
}