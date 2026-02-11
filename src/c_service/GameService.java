package c_service;

import d_model.Spaceship;
import exceptions.ValidationException;
import d_file.FileLogger;

import java.util.Scanner;

public class GameService {
    private final FileLogger fileLogger = new FileLogger();
    //Current Ship in use, reset when new game.
    private Spaceship spaceship;
    private int attempts;

    // Ship Creation
    public void handleShipCreation(String shipsName, String captainsName) {
        Spaceship spaceship = new Spaceship(shipsName, captainsName);
        validateShipName(spaceship);
        setSpaceShip(spaceship);
    }

    public void validateShipName(Spaceship spaceship) throws ValidationException {
        if (spaceship == null) {
            throw new ValidationException("Spaceship is null");
        }
        if (spaceship.getShipsName() == null || spaceship.getShipsName().isBlank()) {
            throw new ValidationException("Spaceship lacks a name");
        }
        if (spaceship.getCaptainsName() == null || spaceship.getCaptainsName().isBlank()) {
            throw new ValidationException("Spaceship lacks a captain with a name");
        }
    }

    public int parseChoice(String choice) throws NumberFormatException {
        try {
            int eventChoice = Integer.parseInt(choice);
            validateEventChoice(eventChoice);
            return eventChoice;
        } catch (NumberFormatException e){
            fileLogger.updateStringLog("Exception: A word and not an number has been typed in");
            throw new NumberFormatException("A word and not an number has been typed in");
        }

    }

    private void validateEventChoice(int eventChoice)throws ValidationException{
        if(eventChoice == 1 || eventChoice == 2){
            return;
        }
        fileLogger.updateStringLog("Exception: Not a valid EventChoice");
        throw new ValidationException("Not a valid EventChoice");
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

    public boolean getShipRepairKitUsed() {
        return this.spaceship.getRepairKitUsed();
    }

    public void createLogFile(String shipName){
        fileLogger.CreateLogFile(shipName);
    }


    public void eventEnded (){
        fileLogger.WriteLogFile(spaceship.getShipsName(), spaceship.getCaptainsName(), spaceship.getEventStageEnum());
    }
}
