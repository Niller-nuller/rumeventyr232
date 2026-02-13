package c_service;

import b_controller.GameController;
import d_model.EventStage;
import d_model.Spaceship;
import exceptions.CriticalStatusException;
import exceptions.IllegalGameState;
import exceptions.InvalidTradeException;
import exceptions.ValidationException;
import d_file.FileLogger;

import static d_model.EventStage.*;

public class GameService {
    private final FileLogger fileLogger = new FileLogger();
    //Current Ship in use, reset when new game.
    private Spaceship spaceship;
    //private GameController gameController = new GameController();
    // Ship Creation
    public void handleShipCreation(String shipsName, String captainsName) {
        Spaceship spaceship = new Spaceship(shipsName, captainsName);
        validateShipName(spaceship);
        setSpaceShip(spaceship);
    }

    public void validateShipName(Spaceship spaceship) throws ValidationException, IllegalArgumentException  {
        try {
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
        }catch (IllegalArgumentException e) {
            callTheFileLogger("The player is putting in some very illigal Argument");
            throw new IllegalArgumentException("What ever you are doing stop it");   }
    }
//--------------------------Event Choice Validation-------------------------------------------------------------------//
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
            callTheFileLogger("The ship has died and you have perished along with it");
            throw new IllegalGameState("The ship has fallen silent, a hollow corpse in the void. No signal answers you. No light finds you. You are alone among the cold, unblinking stars.");
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
    public void changeEventStage(){
        clearConsole();
        if(spaceship.getEventStageEnum() == EVENT_STAGE_1){
            spaceship.setEventStage(EVENT_STAGE_2);
            return;
        }
        if(spaceship.getEventStageEnum() == EVENT_STAGE_2){
            spaceship.setEventStage(EVENT_STAGE_3);
            return;
        }
    }
//------------------Event resources 1----------------------------------------------------------------------------------//
    public int getRandomNumber (int min, int max) {
        int range = max - min + 1;
        return (int)(Math.random() * range) + min;
    }
    private void isShipDead(){
        if(spaceship.getFuel() <= 0 || spaceship.getIntegrity() <= 0){
            setShipDeadStatus();
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
//    public Boolean repair(){
//        return true;
//    }
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
    public boolean getShipRepairKitUsed() {
        return this.spaceship.getRepairKitUsed();
    }
    public Enum<EventStage> getEventStatus () {
        return spaceship.getEventStageEnum();
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
    //--------------Service LogFile---------------------------------------------------------------------------------------//
    public void createLogFile(String shipName){
        fileLogger.CreateLogFile(shipName);
    }
    public void callTheFileLogger(String message){
        fileLogger.updateStringLog("Exception: " + message);
    }
    public void eventEnded (){
        fileLogger.WriteLogFile(spaceship.getShipsName(), spaceship.getCaptainsName(), spaceship.getEventStageEnum(), spaceship.getIsDead());
    }
    public void printLogFile () {
        fileLogger.printLogToConsole(spaceship.getShipsName());
    }
    //-----------------------------------------------------------------------------------------------------//

    public void spaceshipDied() {
        System.exit(0);
    }


    // Clear Console
    // Source - https://stackoverflow.com/a/17015039
// Posted by Dyndrilliac, modified by community. See post 'Timeline' for change history
// Retrieved 2026-02-13, License - CC BY-SA 4.0

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

}
