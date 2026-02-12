package a_ui;

import b_controller.GameController;
import exceptions.InvalidTradeException;
import exceptions.ValidationException;

import java.util.Scanner;

public class GameDisplay {


    private static GameController gameController = new GameController();
    private final Scanner userInput = new Scanner(System.in);


    public void Welcome() {
        while (true) {
            try {
                System.out.println("=================================================");
                System.out.println("    RUMEVENTYR - EXCEPTION RESCUE MISSION        ");
                System.out.println("=================================================");
                System.out.println("Welcome aboard captain.");
                System.out.println("Please enter your name.");
                System.out.print(">");
                String captainsName = userInput.nextLine();
                System.out.println("Please enter your ships name.");
                System.out.print(">");
                String shipsName = userInput.nextLine();
                gameController.handleShipCreation(shipsName, captainsName);
                gameController.createLogFile(shipsName);
                break;
            } catch (ValidationException e) {

                System.out.println(e.getMessage());
            }
        }
    }

    public void printStatus() {
        System.out.println("-------------------------------------------------");
        System.out.println("Status");
        System.out.println("Fuel       = " + gameController.getShipFuel());
        System.out.println("Integrity  = " + gameController.getShipIntegrity());
        System.out.println("SpareParts = " + gameController.getShipSpareParts());
        System.out.println("Shield     = " + gameController.getShipShieldLevel());
        if (!gameController.getShipRepairKitUsed()) {
            System.out.println("Repair Kit = Not used");
        } else {
            System.out.println("Repair Kit = Used");
        }
        System.out.println("-------------------------------------------------");
    }

    public void printEvent1() {
        System.out.println("WARNING VOIDSTORM APPROACHING");
        while (true) {
            System.out.println("Captain, please choose an action");
            System.out.println("The first choice is to fly through the storm at great risk");
            System.out.println("The second choice is to take evasive maneuvers at the cost of fuel");
            System.out.print("Write 1 or 2 for your choice of action:");
            int eventChoice = 0;
            try {
                eventChoice = gameController.getEventChoice(userInput.nextLine());
            } catch (NumberFormatException | ValidationException e) {
                System.out.println(e.getMessage());
            }
            if (eventChoice == 1) {
                gameController.FlyThroughTheStorm();
                break;
            } else if (eventChoice == 2) {
                gameController.TakeEvasiveAction();
                break;
            }
        }
    }

    public void printEvent2() {
        System.out.println("FOREIGN CONNECTION IS ESTABLISHED");
        System.out.println("..........");
        System.out.println("..........");
        System.out.println("Loading...");
        System.out.println("..........");
        System.out.println("..........");
        System.out.println("Message recived");
        System.out.println("Space khajiit got wares if you got spare parts");
        boolean running = true;
        while (running) {
            System.out.println("Hairball incoperated");
            System.out.println("Firstly you can buy fuel for spare parts");
            System.out.println("(Special offer only for you my friend) secondly you can buy shield lvl one for four spare parts");
            System.out.println("Thirdly you can leave empty handed you monster");
            System.out.print("Write 1, 2 or 3 for your choice of action:");
            int eventChoice = 0;
            try {
                eventChoice = gameController.getTradeEventChoice(userInput.nextLine());
            } catch (NumberFormatException | ValidationException e) {
                switch (eventChoice) {
                    case 1:
                        tradeForFuelSubMenu();
                        running = false;
                        break;
                    case 2:
                    case 3:
                }
            }

        }
    }
    public void tradeForFuelSubMenu(){
        while(true) {
            try {
                System.out.print("How many spare parts do you wish to trade?");
                gameController.tradeForFuel(gameController.parseChoice(userInput.nextLine()));
                break;
            } catch (InvalidTradeException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void spaceshipHasDied() {
        System.out.println("Spaceship has been destroyed! :(");
        System.out.println("You reached: " + gameController.currentEventStage());
        System.out.println("Your spaceships journey has been saved to: " + gameController.getShipName() + ".txt");
    }

    public void printOutcomeOfForethoughtTheStorm() {
        System.out.println("Flying into the storm" +
                "Storm Damage Calculates");
        printStatus();
    }

    public void printOutComeOfEvasiveAction(int damage) {
        System.out.println("Evading the storm");
        System.out.println("Storm Damage Calculates");
        System.out.println("Fuel usage: 20");
        System.out.println("Damage taken: " + damage);
        printStatus();
    }

    public void printException(Exception e) {
        System.out.println(e.getMessage());
    }


}
