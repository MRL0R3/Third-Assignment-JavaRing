package org.project.location;

import java.util.ArrayList;

import org.project.entity.enemies.Enemy;

public class Location {
    private String name;

    private ArrayList<Enemy> enemies;
    private ArrayList<Location> locations;

    public Location(ArrayList<Location> locations, ArrayList<Enemy> enemies) {
        this.locations = locations;
        this.enemies = enemies;
    }

    /*
    TODO: (BONUS) RESET EACH LOCATION AFTER PLAYER LEAVES
    */

    public String getName() {
        return name;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
