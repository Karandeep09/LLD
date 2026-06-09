package entity;

import interfaces.IButton;
import service.ElevatorController;

public class ElevatorButton implements IButton {
    private final ElevatorController elevatorController;
    private final int destinationFloor;

    public ElevatorButton(ElevatorController elevatorController, int destinationFloor) {
        this.elevatorController = elevatorController;
        this.destinationFloor = destinationFloor;
    }

    @Override
    public void press() {
        System.out.println("Floor " + destinationFloor + " selected inside Elevator "
                + elevatorController.getElevator().getId());
        elevatorController.addDestination(destinationFloor);
    }
}
