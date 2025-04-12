package org.project;

import org.project.entity.players.*;
import org.project.location.Location;
import java.util.Scanner;
// import org.project.entity.object.consumables.Consumable; // Ensure this matches the actual package of Consumable

public class GameEngine {
    private Player player;
    private Location currentLocation;
    private Scanner scanner = new Scanner(System.in);
    private boolean gameRunning = true;

    public void startGame() {
        initializePlayer();
        initializeLocations();
        
        while (gameRunning && player.isAlive()) {
            displayMainMenu();
            handleMainMenuInput();
        }
        
        endGame();
    }

    private void initializePlayer() {
        System.out.println("=== CHARACTER CREATION ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println("Choose your class:");
        System.out.println("1. Knight\n2. Wizard\n3. Assassin");
        int classChoice = scanner.nextInt();
        
        player = switch (classChoice) {
            case 1 -> new Knight(name);
            // case 2 -> new Wizard(name);
            // case 3 -> new Assassin(name);
            default -> new Knight(name);
        };
        
        System.out.printf("%s the %s enters the Java Ring!%n", 
            player.getName(), player.getClass().getSimpleName());
    }

    private void initializeLocations() {
        currentLocation = new Location("Forgotten Forest", 
            "A dense woodland shrouded in mist");
    }

    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Location: " + currentLocation.getName());
        System.out.println("1. Explore");
        System.out.println("2. Character Info");
        System.out.println("3. Inventory");
        System.out.println("4. Quit");
        System.out.print("Choose an action: ");
    }

    private void handleMainMenuInput() {
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1 -> explore();
            case 2 -> displayCharacterInfo();
            case 3 -> manageInventory();
            case 4 -> gameRunning = false;
            default -> System.out.println("Invalid choice!");
        }
    }

    private void explore() {
        System.out.println("\nYou venture deeper into " + currentLocation.getName());
        
        if (Math.random() < 0.7) { // 70% encounter chance
            startCombat(currentLocation.generateEnemy());
        } else {
            System.out.println("The path is quiet for now...");
            currentLocation.increaseDanger();
        }
    }

    private void startCombat(Enemy enemy) {
        System.out.printf("%nA wild %s appears!%n", enemy.getName());
        
        while (player.isAlive() && enemy.isAlive()) {
            playerTurn(enemy);
            if (enemy.isAlive()) {
                enemyTurn(enemy);
            }
        }
        
        if (player.isAlive()) {
            System.out.printf("You defeated the %s!%n", enemy.getName());
            player.gainExperience(enemy.getExpReward());
            currentLocation.increaseDanger();
        }
    }

    private void playerTurn(Enemy enemy) {
        System.out.println("\n=== YOUR TURN ===");
        System.out.printf("HP: %d/%d | Enemy HP: %d%n",
            player.getHealth(), player.getMaxHealth(), enemy.getHealth());
        
        System.out.println("1. Attack");
        System.out.println("2. Defend");
        System.out.println("3. Special Ability");
        System.out.println("4. Use Item");
        System.out.print("Choose action: ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1 -> player.attack(enemy);
            case 2 -> player.defend();
            case 3 -> player.useSpecialAbility(enemy);
            case 4 -> useItemInCombat();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void enemyTurn(Enemy enemy) {
        System.out.printf("%n=== %s's TURN ===%n", enemy.getName());
        enemy.attack(player);
    }

    private void useItemInCombat() {
        if (player.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty!");
            return;
        }
        
        System.out.println("\nAvailable items:");
        for (int i = 0; i < player.getInventory().size(); i++) {
            Consumable item = player.getInventory().get(i);
            System.out.printf("%d. %s (%d uses)%n",
                i + 1, item.getName(), item.getRemainingUses());
        }
        
        System.out.print("Select item (0 to cancel): ");
        int choice = scanner.nextInt() - 1;
        
        if (choice >= 0 && choice < player.getInventory().size()) {
            player.useItem(choice);
        }
    }

    private void displayCharacterInfo() {
        System.out.println("\n=== CHARACTER SHEET ===");
        System.out.printf("Name: %s%n", player.getName());
        System.out.printf("Class: %s%n", player.getClass().getSimpleName());
        System.out.printf("Level: %d%n", player.getLevel());
        System.out.printf("HP: %d/%d%n", player.getHealth(), player.getMaxHealth());
        System.out.printf("XP: %d/%d%n", 
            player.getExperience(), player.getLevel() * 100);
        System.out.printf("Weapon: %s%n", player.getWeapon().getDescription());
        System.out.printf("Armor: %s%n", player.getArmor().getDescription());
    }

    private void manageInventory() {
        // Similar to combat item menu but with repair options
        // Implementation omitted for brevity
    }

    private void endGame() {
        if (player.isAlive()) {
            System.out.println("You leave the Java Ring... for now.");
        } else {
            System.out.println("GAME OVER");
        }
    }

    public static void main(String[] args) {
        new GameEngine().startGame();
    }
}