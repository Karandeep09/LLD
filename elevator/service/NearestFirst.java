package service;
import interfaces.ElevatorSchedulerStrategy;
import service.ElevatorController;
import java.util.List;
import enums.Direction;
public class NearestFirst implements ElevatorSchedulerStrategy {
    @Override
    public ElevatorController scheduleElevator(List<ElevatorController> elevatorControllers, int requestedFloor, Direction direction) {
        ElevatorController nearestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorController elevatorController : elevatorControllers) {
            boolean isMovingTowardsRequest = (direction == Direction.UP && elevatorController.getDirection() == Direction.UP && elevatorController.getCurrentFloor() <= requestedFloor) ||
                                            (direction == Direction.DOWN && elevatorController.getDirection() == Direction.DOWN && elevatorController.getCurrentFloor() >= requestedFloor) ||
                                            (elevatorController.getDirection() == Direction.IDLE);
            int distance = Math.abs(elevatorController.getCurrentFloor() - requestedFloor);
            if (isMovingTowardsRequest && distance < minDistance) {
                minDistance = distance;
                nearestElevator = elevatorController;
            }
        }

        if (nearestElevator == null) {
            // If no elevator is moving towards the request, find the nearest idle elevator
            for (ElevatorController elevatorController : elevatorControllers) {
                if (elevatorController.getDirection() == Direction.IDLE) {
                    int distance = Math.abs(elevatorController.getCurrentFloor() - requestedFloor);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestElevator = elevatorController;
                    }
                }
            }
        }
        if (nearestElevator == null) {
            // If no idle elevator is found, return the first elevator (or handle as per your requirement)
            nearestElevator = elevatorControllers.get(0);
        }

        return nearestElevator;
    }
}