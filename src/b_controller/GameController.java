package b_controller;

import a_ui.GameDisplay;
import c_service.GameService;
import d_model.EventStage;
import exceptions.CriticalStatusException;
import exceptions.IllegalGameState;
import exceptions.InvalidTradeException;
import exceptions.ValidationException;

public class GameController {
    private final GameDisplay gameDisplay = new GameDisplay();
    private final GameService gameService = new GameService();

    public int getShipFuel() {
        return gameService.getShipFuel();
    }

    public int getShipIntegrity() {
        return gameService.getShipIntegrity();
    }

    public int getShipSpareParts() {
        return gameService.getShipSpareParts();
    }

    public int getShipShieldLevel() {
        return gameService.getShipShieldLevel();
    }

    public String getShipName() {
        return gameService.getShip().getShipsName();
    }

    public boolean getShipRepairKitUsed() {
        return gameService.getShipRepairKitUsed();
    }

    public void createLogFile(String shipsName) {
        gameService.createLogFile(shipsName);
    }
    //Game start---------------------------------------------------
    public void startGame() {
        gameDisplay.Welcome();
        gameDisplay.printStatus();
        startEvent1();
    }
    public void handleShipCreation(String shipsName, String captainsName) {
        gameService.handleShipCreation(shipsName, captainsName);
    }
    //Event1---------------------------------------------------------
    public void startEvent1() {
        gameDisplay.printEvent1();
    }

    public int getEventChoice(String choice) {
        try {
            int eventChoice = gameService.parseChoice(choice);
            gameService.validateEventChoice(eventChoice);
            return eventChoice;
        } catch (NumberFormatException | ValidationException e) {
            throw e;
        }
    }

    public void FlyThroughTheStorm() {
        int damagebase = gameService.getStormDamage();
        double multiplyier = gameService.getRandomNumber(5, 50) / 100.00;
        int damage = (int) (damagebase * (1 - multiplyier));
        int shield = getShipShieldLevel();
        int integrity = getShipIntegrity();

        if (shield >= damage) {
            gameService.setShipShieldLevel(shield - damage);
        } else {
            int remainingDamage = damage - shield;
            gameService.setShipIntegrity(integrity - remainingDamage);
        }
        gameDisplay.printOutcomeOfForethoughtTheStorm(damage);
        atEventEnd();
        startEvent2();
    }

    public void TakeEvasiveAction() {
        if (getShipFuel() >= 20) {
            gameService.setShipFuel(getShipFuel() - 20);
            int damage = 0;
            if (gameService.getRandomNumber(1, 100) > 67) {
                if (gameService.getShipShieldLevel() > 0) {
                    damage = (gameService.getStormDamage() * (1 - (gameService.getRandomNumber(75, 100)/100)));
                    gameService.setShipIntegrity(getShipIntegrity() - damage);
                } else {
                    damage = (gameService.getStormDamage() * (1 - gameService.getRandomNumber(25, 50)/100));
                    gameService.setShipIntegrity(getShipIntegrity() - damage);
                }
            }
            gameDisplay.printOutComeOfEvasiveAction(damage);
        }
        atEventEnd();
        startEvent2();
    }

   //Event2-----------------------------------
    public void startEvent2() {
        gameDisplay.printEvent2();
    }

    public void tradeForFuel(int amountToTrade) {
        try {
            gameService.validateBuy(amountToTrade);
        } catch (IllegalArgumentException | InvalidTradeException e) {
            throw e;
        }
        gameService.setShipFuel(gameService.getShipFuel() + (5 * amountToTrade));
    }

    public void tradeForShield() {
        try {
            gameService.validateBuy(4);
            gameService.validateShieldBuy();
        } catch (IllegalArgumentException | InvalidTradeException e) {
            throw e;
        }
        gameService.setShipShieldLevel(1);
    }

    public int getTradeEventChoice(String choice) {
        try {
            int eventChoice = gameService.parseChoice(choice);
            gameService.validateTradeChoice(eventChoice);
            return eventChoice;
        } catch (NumberFormatException | ValidationException e) {
            throw e;
        }
    }

    public void continueFromTrader(){
        atEventEnd();
        startEvent3();
    }
    //Event3--------------------------------------------------------------
    public void startEvent3() {
        gameDisplay.printEvent3();
    }

    public void repairKitUsed(){
        gameService.repairKitUse();
    }

    public void repairTry(){
       gameService.repairTry();
    }

    public void repairFailure(){
        gameDisplay.printRepairFailure();
    }

    public Enum<EventStage> currentEventStage() {
        return gameService.getEventStatus();
    }

    public int parseChoice(String choice){
        try{
            return gameService.parseChoice(choice);
        } catch (NumberFormatException e) {
            throw e;
        }
    }
    public void atEventEnd() {
        try {
            gameService.checkCriticalStatus();
            if (gameService.getShipBoolean()) {
                gameService.eventEnded();
                gameDisplay.spaceshipHasDied();
                gameService.spaceshipDied();
            }
        } catch (CriticalStatusException | IllegalGameState e) {
            gameDisplay.printException(e);
        }
    }
}

