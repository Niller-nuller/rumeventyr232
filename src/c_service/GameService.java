package c_service;

import b_controller.GameController;
import d_model.EventStage;
import d_model.Spaceship;
import exceptions.CriticalStatusException;
import exceptions.IllegalGameState;
import exceptions.InvalidTradeException;
import exceptions.ValidationException;
import d_file.FileLogger;

public class GameService {
    private final FileLogger fileLogger = new FileLogger();
    //Current Ship in use, reset when new game.
    private Spaceship spaceship;

    // Ship Creation
    public void handleShipCreation(String shipsName, String captainsName) {
        Spaceship spaceship = new Spaceship(shipsName, captainsName);
        validateShipName(spaceship);
        setSpaceShip(spaceship);
    }

    public void validateShipName(Spaceship spaceship) throws ValidationException {
        if (spaceship == null) {
            callTheFileLogger("Spaceship is null");
            throw new ValidationException("Spaceship is null");
        }
        if (spaceship.getShipsName() == null || spaceship.getShipsName().isBlank()) {
            callTheFileLogger("Spaceship lacks a name");
            throw new ValidationException("Spaceship lacks a name");
        }
        if (spaceship.getCaptainsName() == null || spaceship.getCaptainsName().isBlank()) {
            callTheFileLogger("Spaceship lacks a captain with a name");
            throw new ValidationException("Spaceship lacks a captain with a name");
        }
    }
//--------------------------Event Validation-------------------------------------------------------------------//
    public int parseChoice(String choice) throws NumberFormatException {
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e){
            callTheFileLogger("A word and not an number has been typed in");
            throw new NumberFormatException("A word and not an number has been typed in");
        }
    }
    public void validateEventChoice(int eventChoice)throws ValidationException{
        if(eventChoice == 1 || eventChoice == 2){
            return;
        }
        callTheFileLogger("Not a valid EventChoice");
        throw new ValidationException("Not a valid EventChoice");
    }
    public void validateTradeChoice(int eventChoice)throws ValidationException{
        if(eventChoice == 1 || eventChoice == 2 || eventChoice == 3){
            return;
        }
        callTheFileLogger("Not a valid EventChoice");
        throw new ValidationException("Not a valid EventChoice");
    }

//------------------------------------------------------------------------------------------------------//
    public void checkCriticalStatus(){
        isShipDead();
        if(spaceship.getIsDead()){
            callTheFileLogger("The ship has fallen silent, a hollow corpse in the void. No signal answers you. No light finds you. You are alone among the cold, unblinking stars.");
            throw new IllegalGameState("The ship has died and you have perished along with it");
        }
        if(spaceship.getIntegrity() <= 20 && spaceship.getFuel() <= 10){
            callTheFileLogger("You are critically low on both fuel and ship integrity");
            throw new CriticalStatusException("You are critically low on both fuel and ship integrity");
        }
        if(spaceship.getIntegrity() <= 20){
            callTheFileLogger("Integrity is almost breached");
            throw new CriticalStatusException("Integrity is almost breached");
        }
        if(spaceship.getFuel() <= 10){
            callTheFileLogger("CriticalStatusException ");
            throw new CriticalStatusException("You are almost out of fuel");
        }
    }
//------------------Event resources 1----------------------------------------------------------------------------------//
    public int getRandomNumber (int min, int max) {
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }
    private void isShipDead(){
        if(spaceship.getFuel() == 0 || spaceship.getIntegrity() == 0){
            spaceship.setIsDead(true);
        }
    }
    public int getStormDamage () {
        return 100;
    }
//-----------------Event resources 2------------------------------------------------------------------------------------------//

    public void validateBuy(int amount)throws IllegalArgumentException, InvalidTradeException{
        if(amount <= 0){
            callTheFileLogger("You can't trade with nothing or less then nothing");
            throw new IllegalArgumentException("You can't trade with nothing or less then nothing");
        }
        if(spaceship.getSpareParts() < amount){
            callTheFileLogger("Not enough spare parts");
            throw new InvalidTradeException("You don't have enough spare parts");
        }
        spaceship.setSpareParts(spaceship.getSpareParts() - amount);
    }
    public void validateShieldBuy()throws InvalidTradeException{
        if(spaceship.getShieldLevel() == 1){
            callTheFileLogger("Not enough shield level");
            throw new InvalidTradeException("Already have shield level one");
        }
    }
//-------------------Event resources 3-------------------------------------------------------------------------------------------//

    public void repairKitUse(){
        spaceship.setIntegrity(spaceship.getIntegrity() + 20);
        spaceship.setRepairKitUsed(true);
    }

    public void repairTry() throws CriticalStatusException {
        int failures = 0;
        for (int i = 0; i < 2; i++) {
            if (getRandomNumber(60, 100) > 60){
                failures++;
                if (failures == 2){
                    throw new CriticalStatusException("The crew failed to repair the engine");
                }
                spaceship.setIntegrity(spaceship.getIntegrity() - 15);
            } else {
                break;
            }
        }
    }
    public Spaceship getShip() {
        return spaceship;
    }
    private void setSpaceShip(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    public int getShipFuel() {
        return this.spaceship.getFuel();
    }

    public int getShipIntegrity() {
        return this.spaceship.getIntegrity();
    }

    public int getShipSpareParts() {
        return this.spaceship.getSpareParts();
    }

    public int getShipShieldLevel() {
        return this.spaceship.getShieldLevel();
    }
    public boolean getShipBoolean(){
        return spaceship.getIsDead();
    }

    public boolean getShipRepairKitUsed() {
        return this.spaceship.getRepairKitUsed();
    }
    public void setSpareParts(int spareParts) {
        spaceship.setSpareParts(spareParts);
    }
    public void setShipFuel(int fuel){
        spaceship.setFuel(fuel);
    }
    public void setShipShieldLevel(int amount) {
        spaceship.setShieldLevel(amount);
    }

    public void setShipIntegrity(int integrity){
        spaceship.setIntegrity(integrity);
    }


    public void setShipDeadStatus () {
        spaceship.setIsDead(true);
    }
    public void createLogFile(String shipName){
        fileLogger.CreateLogFile(shipName);
    }
    public void callTheFileLogger(String message){
        fileLogger.updateStringLog("Exception: " + message);
    }
    public void eventEnded (){
        fileLogger.WriteLogFile(spaceship.getShipsName(), spaceship.getCaptainsName(), spaceship.getEventStageEnum(), spaceship.getIsDead());
    }


    public void spaceshipDied() {
        System.exit(0);
    }

    public Enum<EventStage> getEventStatus () {
        return spaceship.getEventStageEnum();
    }

}
