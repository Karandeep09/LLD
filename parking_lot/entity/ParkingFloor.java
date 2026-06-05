package entity;

import java.util.*;
import enums.SpotType;
import service.SpotManager;

public class ParkingFloor {
    private String floor_no;
    private Map<SpotType, SpotManager> spotManager;
    
    public ParkingFloor(String floor_no, Map<SpotType, SpotManager> spotManager) {
        this.floor_no = floor_no;
        this.spotManager = spotManager;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }

    public Map<SpotType, SpotManager> getSpotManager() {
        return spotManager;
    }

    public void setSpotManager(Map<SpotType, SpotManager> spotManager) {
        this.spotManager = spotManager;
    }

    public void addSpotManager(SpotType type, SpotManager manager) {
        this.spotManager.put(type, manager);
    }
   
    public boolean isFull(SpotType spotType) {
        SpotManager manager = spotManager.get(spotType);
        if (manager != null && !manager.getFreeSpots().isEmpty()) {
            return false;
        }
        return true;
    }
    
}