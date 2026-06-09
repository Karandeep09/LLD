
package entity;
import enums.Direction;
import service.ElevatorController;

public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction direction;
    private final ElevatorController elevatorController;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.elevatorController = new ElevatorController(this);
    }
    public int getId() {
        return id;
    }
    public synchronized int getCurrentFloor() {
        return currentFloor;
    }
    public synchronized Direction getDirection() {
        return direction;
    }

    public synchronized void setCurrentFloor(int floor) {
        this.currentFloor = floor;
    }
    public synchronized void setDirection(Direction direction) {
        if (this.direction == direction) {
            return;
        }
        this.direction = direction;
        System.out.println("Elevator " + id + " is now moving " + direction);
    }

    public ElevatorController getElevatorController() {
        return elevatorController;
    }
}
