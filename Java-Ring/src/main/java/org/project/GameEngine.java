package org.project;

import org.project.entity.enemies.Enemy;
import org.project.entity.players.*;
import org.project.location.Location;
import org.project.object.consumables.Consumable;
import java.util.Scanner;

/**
 * Main game engine class that controls the game flow and manages player interactions.
 * Handles initialization, game loop, combat, and menu systems.
 */
public class GameEngine {
    // Game state variables
    private Player player;          // Current player character
    private Location currentLocation; // Active game location
    private Scanner scanner = new Scanner(System.in); // Input handler
    private boolean gameRunning = true; // Main game loop flag

    /**
     * Starts the main game loop and manages the game lifecycle
     */
    public void startGame() {
        initializePlayer();     // Set up player character
        initializeLocations();  // Initialize game world

        // Main game loop - runs while game is active and player is alive
        while (gameRunning && player.isAlive()) {
            displayMainMenu();      // Show player options
            handleMainMenuInput();   // Process player choice
        }

        endGame(); // Handle game conclusion
    }

    /**
     * Initializes player character with name and class selection
     */
    private void initializePlayer() {
        System.out.println("=== CHARACTER CREATION ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Class selection menu
        System.out.println("Choose your class:");
        System.out.println("1. Knight\n2. Wizard\n3. Assassin");

        int classChoice = Integer.parseInt(scanner.nextLine());

        // Create player based on class choice
        player = switch (classChoice) {
            case 1 -> new Knight(name);  // Tank class
            case 2 -> new Wizard(name);  // Mage class
            default -> new Knight(name); // Default to Knight
        };

        System.out.printf("%s the %s enters the Java Ring!%n",
                player.getName(), player.getClass().getSimpleName());
    }

    /**
     * Initializes game locations and starting area
     */
    private void initializeLocations() {
        // Create starting location with description
        currentLocation = new Location("Forgotten Forest",
                "A dense woodland shrouded in mist");
    }

    /**
     * Displays the main game menu options
     */
    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Location: " + currentLocation.getName());
        System.out.println("1. Explore");       // Risk encounter
        System.out.println("2. Character Info"); // View stats
        System.out.println("3. Quit");          // Exit game
        System.out.print("Choose an action: ");
    }

    /**
     * Handles player input from main menu
     */
    private void handleMainMenuInput() {
        int choice = getValidInput(1, 3); // Get validated input

        switch (choice) {
            case 1 -> explore();            // Enter exploration
            case 2 -> displayCharacterInfo(); // Show character sheet
            case 3 -> gameRunning = false;   // Quit game
            default -> System.out.println("Invalid choice!");
        }
    }

    /**
     * Handles exploration logic and random encounters
     */
    private void explore() {
        System.out.println("\nYou venture deeper into " + currentLocation.getName());

        // 70% chance of enemy encounter
        if (Math.random() < 0.7) {
            Enemy enemy = currentLocation.generateEnemy();
            if (enemy != null) {
                System.out.println("Encountered: " + enemy.getDescription());
                startCombat(enemy); // Begin combat if enemy exists
            } else {
                System.out.println("The enemy mysteriously vanished...");
            }
        } else {
            System.out.println("The path is clear for now...");
        }
        currentLocation.increaseDanger(); // Scale difficulty
    }

    /**
     * Manages combat between player and enemy
     * @param enemy The enemy to fight
     */
    private void startCombat(Enemy enemy) {
        System.out.printf("%nA wild %s appears!%n", enemy.getName());

        // Combat continues until one combatant is defeated
        while (player.isAlive() && enemy.isAlive()) {
            playerTurn(enemy); // Player action phase

            if (!enemy.isAlive()) break; // Check if enemy defeated

            enemyTurn(enemy); // Enemy action phase
        }

        // Combat resolution
        if (player.isAlive()) {
            System.out.printf("You defeated the %s!%n", enemy.getName());
            player.gainExperience(enemy.getExpReward()); // Award XP
        } else {
            System.out.println("You have been defeated...");
        }
    }

    /**
     * Handles player's turn during combat
     * @param enemy The current combat target
     */
    private void playerTurn(Enemy enemy) {
        System.out.println("\n=== YOUR TURN ===");
        // Display combat status
        System.out.printf("HP: %d/%d | Enemy HP: %d%n",
                player.getHealth(), player.getMaxHealth(), enemy.getHealth());

        // Combat action menu
        System.out.println("1. Attack");
        System.out.println("2. Defend");
        System.out.println("3. Special Ability");
        System.out.println("4. Use Item");
        System.out.print("Choose action: ");

        int choice = getValidInput(1, 4); // Validate input

        // Process player choice
        switch (choice) {
            case 1 -> { // Standard attack
                player.attack(enemy);
                System.out.printf("%s attacks %s!%n",
                        player.getName(), enemy.getName());
            }
            case 2 -> { // Defensive stance
                player.defend();
                System.out.println(player.getName() + " defends!");
            }
            case 3 -> { // Class-specific ability
                if (player instanceof Knight) {
                    ((Knight)player).useSpecialAbility(enemy);
                } else {
                    player.useSpecialAbility(enemy);
                }
                System.out.printf("%s uses special ability on %s!%n",
                        player.getName(), enemy.getName());
            }
            case 4 -> useItemInCombat(); // Inventory usage
            default -> System.out.println("Invalid choice! Lost your turn...");
        }
    }

    /**
     * Validates player input within a specified range
     * @param min Minimum valid value
     * @param max Maximum valid value
     * @return Validated user input
     */
    private int getValidInput(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) return choice;
                System.out.printf("Enter %d-%d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("(Numbers only) ");
            }
        }
    }

    /**
     * Handles enemy's turn during combat
     * @param enemy The attacking enemy
     */
    private void enemyTurn(Enemy enemy) {
        System.out.printf("%n=== %s's TURN ===%n", enemy.getName());
        enemy.attack(player); // Enemy performs attack
    }

    /**
     * Manages item usage during combat
     */
    private void useItemInCombat() {
        if (player.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty!");
            return;
        }

        // Display inventory
        System.out.println("\nAvailable items:");
        for (int i = 0; i < player.getInventory().size(); i++) {
            Consumable item = player.getInventory().get(i);
            System.out.printf("%d. %s (%d uses)%n",
                    i + 1, item.getName(), item.getRemainingUses());
        }

        System.out.print("Select item (0 to cancel): ");
        int choice = getValidInput(0, player.getInventory().size()) - 1;

        if (choice >= 0) {
            player.useItem(choice); // Use selected item
        }
    }

    /**
     * Displays character statistics and equipment
     */
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

    /**
     * Handles game conclusion with appropriate message
     */
    private void endGame() {
        if (player.isAlive()) {
            System.out.println("You leave the Java Ring... for now.");
        } else {
            System.out.println("GAME OVER");
        }
    }

    /**
     * Entry point for the game
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        new GameEngine().startGame(); // Launch game
    }
}