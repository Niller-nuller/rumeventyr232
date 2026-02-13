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
        int damageBase = gameService.getStormDamage();
        double multiplier = gameService.getRandomNumber(5, 100) / 100.00;
        int damage = (int) (damageBase * (1 - multiplier));
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
            int damage;
            if (gameService.getRandomNumber(1, 100) > 67) {
                if (gameService.getShipShieldLevel() > 0) {
                    int damageBase = gameService.getStormDamage();
                    double multiplier = gameService.getRandomNumber(75, 100) / 100.00;
                    damage = (int) (damageBase * (1 - multiplier));
                    gameService.setShipIntegrity(getShipIntegrity() - damage);
                } else {
                    int damageBase = gameService.getStormDamage();
                    double multiplier = gameService.getRandomNumber(50, 75) / 100.00;
                    damage = (int) (damageBase * (1 - multiplier));
                    gameService.setShipIntegrity(getShipIntegrity() - damage);
                }
            } else {
                int damageBase = gameService.getStormDamage();
                double multiplier = gameService.getRandomNumber(25, 50) / 100.00;
                damage = (int) (damageBase * (1 - multiplier));
                gameService.setShipIntegrity(getShipIntegrity() - damage);
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

    public void repairTry() throws CriticalStatusException {
        int failures = 0;
        for (int i = 0; i < 2; i++) {
            repairMSG();
            if (gameService.getRandomNumber(60, 100) > 60){
                failures++;
                repairFailure();
                if (failures == 2){
                    gameService.callTheFileLogger("The crew failed to repair the engine");
                    throw new CriticalStatusException("The crew failed to repair the engine");
                }
                gameService.setShipIntegrity(gameService.getShipIntegrity() - 15);
            } else {
                break;
            }
        }
    }

    public void repairFailure(){
        gameDisplay.printRepairFailure();
    }

    public void repairMSG(){
        gameDisplay.printRepairMSG();
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
        } catch (CriticalStatusException e){
            gameDisplay.printException(e);
            gameService.changeEventStage();
            gameService.eventEnded();
        } catch (IllegalGameState e){
            gameDisplay.printException(e);
            gameDisplay.spaceshipHasDied();
            gameService.eventEnded();
            gameService.printLogFile();
            gameService.spaceshipDied();
        }
        gameService.eventEnded();
        gameService.changeEventStage();
    }

    public void gameEnded () {
        gameService.printLogFile();
    }
}

