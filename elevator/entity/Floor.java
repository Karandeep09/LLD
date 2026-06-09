package entity;
import enums.Direction;
import interfaces.IButton;
import service.ElevatorSystemController;

public class Floor {
    int floorNumber;
    UpButton upButton;
    DownButton downButton;
    public Floor(int floorNumber, ElevatorSystemController elevatorSystemController) {
        this.floorNumber = floorNumber;
        this.upButton = new UpButton(this, elevatorSystemController);
        this.downButton = new DownButton(this, elevatorSystemController);
    }
    public int getFloorNumber() {
        return floorNumber;
    }
    public UpButton getUpButton() {
        return upButton;
    }
    public DownButton getDownButton() {
        return downButton;
    }
}