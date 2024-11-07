package se.biplob.game;

import se.biplob.game.model.*;
import java.util.Scanner;

public class Game {
    private static final String living_Room = "Living room";
    private static final String kitchen_Room = "Kitchen";
    private static final String hall_Room = "Hall room";
    private static final String bed_Room = "Bed room";
    private static final String office_Room = "Office";
    private String current_Position = living_Room;
    private boolean running = true;
    private Scanner sc = new Scanner(System.in);

    public void startGame() {
        welcome_Room_Description();
        System.out.println("Enter your name,Health and Damage ");
        Resident resident = new Resident(sc.nextLine(), sc.nextInt(), sc.nextInt());
        System.out.println("Enters Burglars Health and Damage.(Note: Burglars damage is initially more than resident)");
        Burglar burglar = new Burglar(sc.nextInt(), sc.nextInt());
        sc.nextLine();
        System.out.println(resident);
        System.out.println(burglar);

        while (running) {
            printOptions();
            String input = sc.nextLine();
            processInput(input);
            running=insideRoomOperations(current_Position, resident, burglar);
        }
        sc.close();
    }
    private void welcome_Room_Description() {
        System.out.format("Welcome to My Game.In this game you are in a house with five rooms\n" +
                "# Living room (Its the start point and is the only way to go to another room)\n" +
                "# Hall room (A thief broke into the hall room and you need to fight to win the game)\n" +
                "# Bed room (Nothing special to do here)\n" +
                "# Kitchen (Several utensil are here that can be used to fight with the thief)\n" +
                "# Office (A telephone is kept here needed to call the Police to finish the Game)\n" +
                "Good luck!\n");
    }
    private void printOptions() {
        System.out.format("Choose which direction you want to go.The options are:\n" +
                "# Livingroom # Kitchen # Hallroom # Bedroom # Office\n");
    }
    private void processInput(String input) {
        input = switch (input.trim().toLowerCase()) {
            case "kitchen" -> kitchen_Room;
            case "livingroom" -> living_Room;
            case "hallroom" -> hall_Room;
            case "bedroom" -> bed_Room;
            case "office" -> office_Room;
            default -> "Invalid input";
        };
        if (input.equals("Invalid input")) {
            System.out.println("Invalid input. Please choose a valid room or Type following the given options");
            return;
        }
        if (current_Position.equalsIgnoreCase(living_Room) || input.equalsIgnoreCase(living_Room)) {
            current_Position = input;
        }
        else {
            current_Position = "wrong direction";
            System.out.println(current_Position);
        }
    }
    public boolean insideRoomOperations(String current_Position, Resident resident, Burglar burglar) {
        switch (current_Position) {
            case (living_Room):
                System.out.println("You are in living room now and you can go to another room from here");
                break;
            case (hall_Room):
                if (resident.isConscious()) {
                    return inHallRum(resident, burglar);
                }
            case (kitchen_Room):
                inKitchen(resident);
                break;
            case (bed_Room):
                System.out.println("The burglar is not here! Check another room");
                break;
            case (office_Room):
                    return inOffice(burglar);

            default:
                System.out.println("You need to go back to livingroom first.");
                break;
        }
        return true;
    }
    public boolean inOffice(Burglar burglar) {
        if (burglar.isConscious()) {
            System.out.println("You are in the office. You can ring the police.");
            System.out.println("But You need to make the burglar unconscious and then make a call");
            return true;
        } else {
            System.out.println("You should call the police now.Enter 'yes' to make a call.");
            String action = sc.nextLine();
            if (action.equalsIgnoreCase("yes")) {
                System.out.println("Ring! Ring! Police is On the way. Congratulations! You have won the game. ");
                return false;
            } else {
                System.out.println("You can go to another room");
                return true;
            }
        }
    }
    public void inKitchen(Resident resident) {
        if (!resident.fryingPanFound) {
            System.out.println("The burglar is not here.Do you want to take a pan? Type (yes/no)");
            String action = sc.nextLine();
            if (action.equalsIgnoreCase("yes")) {
                resident.fryingPanFound = true;
                resident.setDamage();
                System.out.println("You took the pan and your new damage is " + resident.getDamage());
            } else
                System.out.println("You can check other room");

        } else
            System.out.println("Nothing to do here. You can go to another room");
    }
    public boolean inHallRum(Resident resident, Burglar burglar) {

        if (burglar.isConscious()) {
            System.out.println("You are in hallrum.Do you want to fight? Type (yes/no)");
            boolean keepFight = true;
            while (keepFight && resident.isConscious() && burglar.isConscious()) {
                String takeAction = sc.nextLine().trim();
                if (takeAction.equalsIgnoreCase("yes")) {
                    resident.punch(burglar);
                    burglar.punch(resident);
                    System.out.println("You both had a fight and the score is: ");
                    System.out.println(resident);
                    System.out.println(burglar);
                    if (!resident.isConscious()) {
                        System.out.println("You loose the game");
                        return false;
                    } else if (resident.isConscious() && burglar.isConscious())
                        System.out.println("You can keep fighting or go to another room. Do you want to fight? Type (yes/no)");
                } else
                    keepFight = false;
            }
        } else {
            System.out.println("Burglar is unconscious. you can go to the office and call the police");
            return true;
        }
        return true;
    }
}
