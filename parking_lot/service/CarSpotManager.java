package service;
import entity.ParkingSpot;
import java.util.*;
public class CarSpotManager extends SpotManager {
    
    public CarSpotManager(List<ParkingSpot> spots, SpotAssignmentStrategy strategy) {
        this.freeSpots = spots;
        this.occupiedSpots = new ArrayList<>();
        this.strategy = strategy;
    }
    @Override
    public synchronized ParkingSpot assignSpot() {
        ParkingSpot spot = strategy.assignSpot(freeSpots);
        if (spot != null && spot.isFree() && spot.reserve()) {
            freeSpots.remove(spot);
            occupiedSpots.add(spot);
            return spot;
        }
        return null;
    }

    @Override
    public synchronized boolean releaseSpot(ParkingSpot spot) {
        if (spot != null && !spot.isFree() && spot.release()) {
            occupiedSpots.remove(spot);
            freeSpots.add(spot);
            return true;
        }
        return false;
    }

    public List<ParkingSpot> getFreeSpots() {
        return freeSpots;
    }
}