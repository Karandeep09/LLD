
package service;
import java.util.*;
import enums.Direction;
import interfaces.ElevatorSchedulerStrategy;

public class ElevatorSystemController {
    List<ElevatorController> elevatorControllers;
    ElevatorSchedulerStrategy schedulerStrategy;
    public ElevatorSystemController(ElevatorSchedulerStrategy schedulerStrategy, List<ElevatorController> elevatorControllers) {
        this.schedulerStrategy = schedulerStrategy;
        this.elevatorControllers = elevatorControllers;
    }
    public ElevatorController dispatchElevator(Direction direction, int floorNumber) {
        ElevatorController selectedElevatorController = schedulerStrategy.scheduleElevator(elevatorControllers, floorNumber, direction);
        System.out.println("Dispatching Elevator " + selectedElevatorController.getElevator().getId() + " to floor " + floorNumber);
        selectedElevatorController.addDestination(floorNumber);
        return selectedElevatorController;
    }
}