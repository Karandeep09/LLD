package service;
import entity.ParkingSpot;
import java.util.*;
public class FloorWiseSpotStrategy implements SpotAssignmentStrategy {
    @Override
    public ParkingSpot assignSpot(List<ParkingSpot> availableSpots) {
        if (availableSpots == null || availableSpots.isEmpty()) {
            return null; // No spots available
        }
        return availableSpots.get(0); // Placeholder logic
    }
}