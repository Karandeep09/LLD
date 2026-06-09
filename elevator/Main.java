import entity.Building;
import entity.ElevatorButton;
import enums.Direction;
import service.ElevatorController;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Building building = new Building(10, 3);

        ElevatorController firstElevator = building.getElevatorSystemController()
                .dispatchElevator(Direction.UP, 1);
        Thread.sleep(1500);
        new ElevatorButton(firstElevator, 7).press();
        Thread.sleep(1500);
        building.getElevatorSystemController().dispatchElevator(Direction.DOWN, 5);
    }
}
