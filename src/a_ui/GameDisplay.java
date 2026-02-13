package a_ui;

import b_controller.GameController;
import exceptions.CriticalStatusException;
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
            } catch (ValidationException | IllegalArgumentException e) {
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
            System.out.print("Write 1 or 2 for your choice of action: ");
            int eventChoice = 0;
            try {
                eventChoice = gameController.getEventChoice(userInput.nextLine().trim());
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

    public void printOutcomeOfForethoughtTheStorm(int damage) {
        System.out.println("Flying into the storm");
        System.out.println("Storm Damage Calculates");
        System.out.println("Damage taken: " + damage);
        printStatus();
    }

    public void printOutComeOfEvasiveAction(int damage) {
        System.out.println("Evading the storm");
        System.out.println("Storm Damage Calculates");
        System.out.println("Fuel usage: 20");
        System.out.println("Damage taken: " + damage);
        printStatus();
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
            System.out.println("---Hairball incorporated---");
            System.out.println("Firstly you can buy fuel for spare parts");
            System.out.println("(Special offer only for you my friend) secondly you can buy shield lvl one for four spare parts");
            System.out.println("Thirdly you can continue on your journey");
            System.out.println("---------------------------");
            System.out.print("Write 1, 2 or 3 for your choice of action: ");
            int eventChoice = 0;
            try {
                eventChoice = gameController.getTradeEventChoice(userInput.nextLine().trim());
                switch (eventChoice) {
                    case 1:
                        tradeForFuelSubMenu();
                        break;
                    case 2:
                        try {
                            gameController.tradeForShield();
                            System.out.println("Successfully traded for shield");
                        } catch (InvalidTradeException e) {
                            System.out.println(e.getMessage());
                        }
                        printStatus();
                        break;
                    case 3:
                        printStatus();
                        running = false;
                        gameController.continueFromTrader();
                }
            } catch (NumberFormatException | ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void tradeForFuelSubMenu() {
        while (true) {
            try {
                System.out.println("How many spare parts do you wish to trade?");
                System.out.print("Amount:");
                String amount = userInput.nextLine().trim();
                gameController.tradeForFuel(gameController.parseChoice(amount));
                System.out.println("Bought " + gameController.parseChoice(amount) * 5 + " fuel successfully");
                printStatus();
                break;
            } catch (InvalidTradeException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printEvent3() {
        System.out.println("CAPTAIN the quantrum extroliator, fusion pusher 3000xd™ engine has broken down");
        System.out.println("Captain do you wish to use our repair kit to give us more legroom to try and fix the engine?");;
        int eventChoice = 0;
        while (true) {
            System.out.println("Captain, please send down an answer. 1 for yes and a 2 for no.");
            System.out.println("----------------------------------------------------------------");
            System.out.print("Captain, please choose an action: ");
            try {
                if (gameController.getEventChoice(userInput.nextLine().trim()) == 1) {
                    gameController.repairKitUsed();
                    System.out.println("Repair kit used");
                    printStatus();
                    break;
                } else {
                    System.out.println("Repair kit not used");
                    break;
                }
            } catch (NumberFormatException | ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            gameController.repairTry();
            System.out.println("Successfully repaired");
        } catch (CriticalStatusException e) {
            System.out.println(e.getMessage());
        }
        gameEnd();
    }
    public void gameEnd(){
        System.out.println();
        System.out.println("Congrats on making it to the end");
        System.out.println("You have finished your journey");
        System.out.println("and reached your destination");
        gameController.atEventEnd();
        gameController.gameEnded();
    }
    public void printRepairFailure() {
        System.out.println("Repair failed. -15 Ship integrity");
    }

    public void printRepairMSG(){
        System.out.println("Trying to repair");
    }

    public void spaceshipHasDied() {
        System.out.println("Spaceship has been destroyed! :(");
        System.out.println("You reached: " + gameController.currentEventStage());
        System.out.println("Your spaceships journey has been saved to: " + gameController.getShipName() + ".txt");
    }
    public void printException(Exception e) {
        System.out.println(e.getMessage());
    }
}
