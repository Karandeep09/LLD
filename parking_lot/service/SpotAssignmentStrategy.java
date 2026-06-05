package service;
import java.util.*;
import entity.ParkingSpot;
public interface SpotAssignmentStrategy {
    ParkingSpot assignSpot(List<ParkingSpot> availableSpots);
}