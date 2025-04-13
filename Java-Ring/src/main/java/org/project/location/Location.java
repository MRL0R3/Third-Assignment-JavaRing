package org.project.location;

import java.util.Random;

import org.project.entity.enemies.Dragon;
import org.project.entity.enemies.Enemy;
import org.project.entity.enemies.Goblin;
import org.project.entity.enemies.Skeleton;
import org.project.object.weapons.Claw;
import org.project.object.weapons.Dagger;
import org.project.object.weapons.RustySword;

public class Location {

    public enum Biome {
        FOREST, CAVE, MOUNTAIN , RUINS
    }

    private String name;
    private String description;
    private int dangerLevel;
    private Random random = new Random();
    private  Biome biome;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.dangerLevel = 1;
        this.biome = biome;
    }

    //    public Enemy generateEnemy() {
//        int scaledHealth = 50 + (dangerLevel * 20);
//        int scaledExp = 30 + (dangerLevel * 10);
//        int enemyType = random.nextInt(3); // 0=Goblin, 1=Skeleton, 2=Dragon
//
//        return switch (enemyType) {
//            case 0 -> new Goblin(scaledHealth, scaledExp, new Dagger());
//            case 1 -> new Skeleton(scaledHealth, scaledExp, new RustySword());
//            case 2 -> new Dragon(scaledHealth, scaledExp, new Claw());
//            default -> throw new IllegalStateException("Invalid enemy type");
//        };
//    }
    public Enemy generateEnemy() {
        try {
            int scaledHealth = 50 + (dangerLevel * 20);
            int scaledExp = 30 + (dangerLevel * 10);
            int enemyType = random.nextInt(3); // 0-2

            System.out.println("Generating enemy type: " + enemyType); // Debug

            return switch (enemyType) {
                case 0 -> new Goblin(scaledHealth, scaledExp, new Dagger());
                case 1 -> new Skeleton(scaledHealth, scaledExp, new RustySword());
                case 2 -> new Dragon(scaledHealth, scaledExp, new Claw());
                default -> throw new IllegalStateException("Invalid enemy type");
            };
        } catch (Exception e) {
            System.err.println("Failed to generate enemy: " + e.getMessage());
            return null; // Or create a default enemy
        }
    }

    private double[] getBiomeWeights() {
        return switch (biome) {
            case FOREST -> new double[]{70, 25, 5};   // 70% Goblin, 25% Skeleton, 5% Dragon
            case CAVE -> new double[]{30, 65, 5};      // More skeletons in caves
            case MOUNTAIN -> new double[]{10, 30, 60}; // Dragons dominate mountains
            case RUINS -> new double[]{40, 55, 5};     // Balanced with more undead
            default -> new double[]{60, 35, 5};
        };
    }


    public void increaseDanger() {
        dangerLevel++;
        System.out.println(name + " grows more dangerous...");
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    // New method to get danger description
    public String getDangerDescription() {
        if (dangerLevel <= 2) {
            return "Safe";
        }
        if (dangerLevel <= 5) {
            return "Risky";
        }
        return "Deadly";
    }
}
