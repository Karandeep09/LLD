package entity;
import enums.Direction;
import interfaces.IButton;
import service.ElevatorSystemController;
public class DownButton implements IButton {
    Floor floor;
    ElevatorSystemController elevatorSystemController;
    public DownButton(Floor floor, ElevatorSystemController elevatorSystemController) {
        this.floor = floor;
        this.elevatorSystemController = elevatorSystemController;
    }

    @Override
    public void press() {
        System.out.println("Down button pressed at floor " + floor.getFloorNumber());
        elevatorSystemController.dispatchElevator(Direction.DOWN, floor.getFloorNumber());
    }
}
