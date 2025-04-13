package org.project;

import org.project.entity.enemies.Enemy;
import org.project.entity.players.*;
import org.project.location.Location;
import org.project.object.consumables.Consumable;

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

        int classChoice = Integer.parseInt(scanner.nextLine());
        player = switch (classChoice) {
            case 1 -> new Knight(name);
             case 2 -> new Wizard(name);
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
        System.out.println("3. Quit");
        System.out.print("Choose an action: ");
    }

    private void handleMainMenuInput() {
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> explore();
            case 2 -> displayCharacterInfo();
            case 3 -> gameRunning = false;
            default -> System.out.println("Invalid choice!");
        }
    }

    private void explore() {
        System.out.println("\nYou venture deeper into " + currentLocation.getName());

        if (Math.random() < 0.7) {
            Enemy enemy = currentLocation.generateEnemy();
            if (enemy != null) {
                System.out.println("Encountered: " + enemy.getDescription());
                startCombat(enemy);
            } else {
                System.out.println("The enemy mysteriously vanished...");
            }
        } else {
            System.out.println("The path is clear for now...");
        }
        currentLocation.increaseDanger();
    }

    private void startCombat(Enemy enemy) {
        System.out.printf("%nA wild %s appears!%n", enemy.getName());

        while (player.isAlive() && enemy.isAlive()) {
            // Add turn counter for debugging
            int turnCounter = 0;
            System.out.println("\n--- Turn " + turnCounter + " ---");
            playerTurn(enemy);

            if (!enemy.isAlive()) break;

            turnCounter++;
            enemyTurn(enemy);
        }

        if (player.isAlive()) {
            System.out.printf("You defeated the %s!%n", enemy.getName());
            player.gainExperience(enemy.getExpReward());
        } else {
            System.out.println("You have been defeated...");
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

        int choice = getValidInput(1, 4); // Using robust input method

        switch (choice) {
            case 1 ->
            {
                player.attack(enemy); // Pass enemy to attack
                System.out.printf("%s attacks %s!%n",
                        player.getName(), enemy.getName());
            }
            case 2 ->
            {
                player.defend();
                System.out.println(player.getName() + " defends!");
            }
            case 3 ->
            {
                if (player instanceof Knight) { ((Knight)player).useSpecialAbility(enemy); } // Pass enemy to special
                else { player.useSpecialAbility(enemy); } // Generic case

                System.out.printf("%s uses special ability on %s!%n",
                        player.getName(), enemy.getName());
            }
            case 4 -> useItemInCombat();
            default ->
            {
                System.out.println("Invalid choice! Lost your turn...");
            }
        }
    }

    // Robust input handling method
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
                    i + 1, ((Consumable) item).getName(), item.getRemainingUses());
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