package entity;
import java.time.LocalDateTime;
import java.util.*;
import enums.*;
public class ParkingLot {
    private List<ParkingFloor> floors;
    private List<EntryGate> entryGates;
    private List<ExitGate> exitGates;

    public ParkingLot() {
        this.floors = new ArrayList<>();
        this.entryGates = new ArrayList<>();
        this.exitGates = new ArrayList<>();
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public void setFloors(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    public List<EntryGate> getEntryGates() {
        return entryGates;
    }

    public void setEntryGates(List<EntryGate> entryGates) {
        this.entryGates = entryGates;
    }

    public List<ExitGate> getExitGates() {
        return exitGates;
    }

    public void setExitGates(List<ExitGate> exitGates) {
        this.exitGates = exitGates;
    }

    public void addFloor(ParkingFloor floor) {
        this.floors.add(floor);
    }

    public void addEntryGate(EntryGate gate) {
        this.entryGates.add(gate);
    }

    public void addExitGate(ExitGate gate) {
        this.exitGates.add(gate);
    }

    public Ticket reserveSpotForVehicle(Vehicle vehicle, EntryGate entryGate) {
        for (ParkingFloor floor : floors) {
            if (!floor.isFull(vehicle.getType().toSpotType())) {
                ParkingSpot spot = floor.getSpotManager().get(vehicle.getType().toSpotType()).assignSpot();
                return entryGate.generateTicket(vehicle, spot, floor);
            }
        }
        return null; // No spots available
    }
}
