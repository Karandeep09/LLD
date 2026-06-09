
package entity;
import enums.Direction;
import service.ElevatorController;

public class Elevator {
    int id;
    int currentFloor;
    Direction direction;
    ElevatorController elevatorController;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.elevatorController = new ElevatorController(this);
    }
    public int getId() {
        return id;
    }
    public int getCurrentFloor() {
        return currentFloor;
    }
    public Direction getDirection() {
        return direction;
    }

    public synchronized void setCurrentFloor(int floor) {
        Thread t = new Thread(() -> {
            try {
                int f;
                for(f = currentFloor; f != floor; f += (floor > currentFloor ? 1 : -1)) {
                    this.currentFloor = f;
                    System.out.println("Elevator " + id + " is now at floor " + currentFloor);
                    Thread.sleep(1000); // Simulate time taken to move between floors
                }
                this.currentFloor = f;
                System.out.println("Elevator " + id + " is now at floor " + currentFloor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
        System.out.println("Elevator " + id + " is now moving " + direction);
    }

    public ElevatorController getElevatorController() {
        return elevatorController;
    }
}