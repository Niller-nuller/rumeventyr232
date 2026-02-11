package d_model;

public class Spaceship {
    private String shipsName;
    private String captainsName;
    private int fuel;
    private int integrity;
    private int spareParts;
    private int shieldLevel;
    private boolean repairKitUsed;
    private Enum<EventStage> eventStageEnum;

    public Spaceship(String shipsName, String captainsName) {
        this.shipsName = shipsName;
        this.captainsName = captainsName;
        this.fuel = 100;
        this.integrity = 100;
        this.spareParts = 10;
        this.shieldLevel = 0;
        this.repairKitUsed = false;
        this.eventStageEnum = EventStage.EVENT_STAGE_1;
    }

    public String getShipsName() {
        return shipsName;
    }
    public String getCaptainsName() {
        return captainsName;
    }
    public int getFuel() {
        return this.fuel;
    }
    public int getIntegrity() {
        return this.integrity;
    }
    public int getSpareParts() {
        return this.spareParts;
    }
    public int getShieldLevel() {
        return this.shieldLevel;
    }
    public boolean getRepairKitUsed() {
        return this.repairKitUsed;
    }
    public Enum<EventStage> getEventStageEnum() {
        return this.eventStageEnum;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }
    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }
    public void setSpareParts(int spareParts) {
        this.spareParts = spareParts;
    }
    public void setShieldLevel(int shieldLevel) {
        this.shieldLevel = shieldLevel;
    }
    public void setRepairKitUsed(boolean repairKitUsed) {
        this.repairKitUsed = repairKitUsed;
    }
    public void setEventStage(EventStage eventStage){
        this.eventStageEnum = eventStage;
    }
}
