package entity;
import enums.Direction;
import interfaces.IButton;
import service.ElevatorSystemController;
import service.ElevatorController;
import java.util.Scanner;
public class UpButton implements IButton {
    Floor floor;
    private final static Scanner scanner = new Scanner(System.in);
    ElevatorSystemController elevatorSystemController;
    public UpButton(Floor floor, ElevatorSystemController elevatorSystemController) {
        this.floor = floor;
        this.elevatorSystemController = elevatorSystemController;
    }

    @Override
    public void press() {
        System.out.println("Up button pressed at floor " + floor.getFloorNumber());
        ElevatorController c = elevatorSystemController.dispatchElevator(Direction.UP, floor.getFloorNumber());
        System.out.println("Enter the destination floor number:");
        int destinationFloor = scanner.nextInt();
        c.addDestination(destinationFloor);
    }
}



