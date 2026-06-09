
package interfaces;
import service.ElevatorController;
import java.util.List;
import enums.Direction;
public interface ElevatorSchedulerStrategy {
    ElevatorController scheduleElevator(List<ElevatorController> elevatorControllers, int requestedFloor, Direction direction);
}