package a_ui;

import b_controller.GameController;
import exceptions.ValidationException;

import java.util.Scanner;

public class GameDisplay {
    
   
    private static GameController gameController = new GameController();



    public void Welcome() {
        try(Scanner userInput = new Scanner(System.in)) {
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
        } catch (ValidationException e){

            System.out.println(e.getMessage());
        }

    }

    private static void PrintStatus(){
        System.out.println("-------------------------------------------------");
        System.out.println("Status");
        System.out.println("Fuel       = " + gameController.getShipFuel());
        System.out.println("Integrity  = " + gameController.getShipIntegrity());
        System.out.println("SpareParts = " + gameController.getShipSpareParts());
        System.out.println("Shield     = " + gameController.getShipShieldLevel());
        if (!gameController.getShipRepairKitUsed()){
            System.out.println("Repair Kit = Not used");
        } else {
            System.out.println("Repair Kit = Used");
        }
        System.out.println("-------------------------------------------------");
    }
    public void printEvent1 () throws NumberFormatException {
        System.out.println("WARNING VOIDSTORM APPROACHING");
        while (true) {
            System.out.println("Captain, please choose an action");
            int eventChoice = 0;
            try(Scanner userInput = new Scanner(System.in)) {
                eventChoice = gameController.getEventChoice(userInput.nextLine());}
            catch (NumberFormatException | ValidationException e){
                System.out.println(e.getMessage());
            }
            if (eventChoice == 1) {
                ///FlyThroughTheStorm();
            System.out.println("Choice1");
            }
            else{
                ///TakeEvasiveAction();
                System.out.println("choice 2");
            }
        }
    }
}
