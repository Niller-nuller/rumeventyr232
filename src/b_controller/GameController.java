package b_controller;

import a_ui.GameDisplay;
import c_service.GameService;
import exceptions.ValidationException;

public class GameController {
    private final GameDisplay gameDisplay = new GameDisplay();
    private final GameService gameService = new GameService();

    public int getShipFuel(){
        return gameService.getShipFuel();
    }
    public int getShipIntegrity(){
        return gameService.getShipIntegrity();
    }
    public int getShipSpareParts(){
        return gameService.getShipSpareParts();
    }
    public int getShipShieldLevel(){
        return gameService.getShipShieldLevel();
    }
    public boolean getShipRepairKitUsed(){
        return gameService.getShipRepairKitUsed();
    }
    public int getEventChoice(String choice){
        try {
        return gameService.parseChoice(choice);
        } catch (NumberFormatException | ValidationException e) {
            throw e;
        }
    }
    public void handleShipCreation(String shipsName, String captainsName){
        gameService.handleShipCreation(shipsName, captainsName);
    }

    public void createLogFile(String shipsName){
        gameService.createLogFile(shipsName);
    }
    public void startGame() {
        gameDisplay.Welcome();
        startEvent1();
    }
    public void startEvent1() {
        gameDisplay.printEvent1();
        gameService.eventEnded();
    }
}
