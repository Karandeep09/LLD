
package entity;
import enums.Direction;
import interfaces.IButton;
import service.ElevatorSystemController;
import service.ElevatorController;
import java.util.Scanner;
public class DownButton implements IButton {
    Floor floor;
    private final static Scanner scanner = new Scanner(System.in);
    ElevatorSystemController elevatorSystemController;
    public DownButton(Floor floor, ElevatorSystemController elevatorSystemController) {
        this.floor = floor;
        this.elevatorSystemController = elevatorSystemController;
    }

    @Override
    public void press() {
        System.out.println("Down button pressed at floor " + floor.getFloorNumber());
        ElevatorController c = elevatorSystemController.dispatchElevator(Direction.DOWN, floor.getFloorNumber());
        System.out.println("Enter the destination floor number:");
        int destinationFloor = scanner.nextInt();
        c.addDestination(destinationFloor);
    }
}