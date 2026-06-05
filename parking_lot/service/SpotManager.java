package service;
import entity.ParkingSpot;
import java.util.*;
public abstract class SpotManager {
    protected List<ParkingSpot> freeSpots;
    protected List<ParkingSpot> occupiedSpots; 
    protected SpotAssignmentStrategy strategy;
    public abstract ParkingSpot assignSpot();
    public abstract boolean releaseSpot(ParkingSpot spot); 
    public abstract List<ParkingSpot> getFreeSpots();
}