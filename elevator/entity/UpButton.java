package entity;
import enums.Direction;
import interfaces.IButton;
import service.ElevatorSystemController;
public class UpButton implements IButton {
    Floor floor;
    ElevatorSystemController elevatorSystemController;
    public UpButton(Floor floor, ElevatorSystemController elevatorSystemController) {
        this.floor = floor;
        this.elevatorSystemController = elevatorSystemController;
    }

    @Override
    public void press() {
        System.out.println("Up button pressed at floor " + floor.getFloorNumber());
        elevatorSystemController.dispatchElevator(Direction.UP, floor.getFloorNumber());
    }
}
