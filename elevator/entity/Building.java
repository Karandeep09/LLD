
package entity;
import java.util.*;
import service.ElevatorSystemController;
import service.NearestFirst;
import java.util.stream.Collectors;
public class Building {
    List<Floor> floors;
    List<Elevator> elevators;
    ElevatorSystemController elevatorSystemController;
    public Building(int numberOfFloors, int numberOfElevators) {
        this.floors = new ArrayList<>();
        
        this.elevators = new ArrayList<>();
        for (int i = 0; i < numberOfElevators; i++) {
            elevators.add(new Elevator(i));
        }
        this.elevatorSystemController = new ElevatorSystemController(new NearestFirst(), this.elevators.stream().map(e -> e.getElevatorController()).collect(Collectors.toList()));
        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(new Floor(i, elevatorSystemController));
        }
    }
    public List<Floor> getFloors() {
        return floors;
    }

    public ElevatorSystemController getElevatorSystemController() {
        return elevatorSystemController;
    }
}
