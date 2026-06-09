package service;
import entity.Elevator;
import enums.Direction;
import java.util.Collections;
import java.util.PriorityQueue;

public class ElevatorController {
   private final PriorityQueue<Integer> downMaxPriorityQueue;
   private final PriorityQueue<Integer> upMinPriorityQueue;
   private final Elevator elevator;
   private boolean moving;

   public ElevatorController(Elevator elevator) {
        downMaxPriorityQueue = new PriorityQueue<>(Collections.reverseOrder());
        upMinPriorityQueue = new PriorityQueue<>();
        this.elevator = elevator;
        this.moving = false;
   }
    public Elevator getElevator() {
        return elevator;
    }
    public Direction getDirection() {
        return elevator.getDirection();
    }
    public int getCurrentFloor() {
        return elevator.getCurrentFloor();
    }
    public synchronized void addDestination(int floor){
        int currentFloor = elevator.getCurrentFloor();
        Direction direction = elevator.getDirection();

        if (floor == currentFloor) {
            System.out.println("Elevator " + elevator.getId() + " is already at floor " + floor);
            return;
        }

        if(direction == Direction.UP) {
            if(floor >= currentFloor){
                upMinPriorityQueue.offer(floor);
            } else {
                downMaxPriorityQueue.offer(floor);
            }
        } else if(direction == Direction.DOWN) {
            if(floor <= currentFloor){
                downMaxPriorityQueue.offer(floor);
            } else {
                upMinPriorityQueue.offer(floor);
            }
        } else {
            if(floor < currentFloor){
                downMaxPriorityQueue.offer(floor);
            } else if (floor > currentFloor){
                upMinPriorityQueue.offer(floor);
            }
        }

        startMovementIfNeeded();
    }

    private void startMovementIfNeeded() {
        if (!moving) {
            moving = true;
            Thread movementThread = new Thread(this::processDestinations);
            movementThread.setName("elevator-" + elevator.getId() + "-movement");
            movementThread.start();
        }
    }

    private void processDestinations() {
        while (true) {
            Integer destination = getNextDestination();
            if (destination == null) {
                return;
            }
            moveToFloor(destination);
        }
    }

    private synchronized Integer getNextDestination() {
        Direction direction = elevator.getDirection();
        Integer destination = null;

        if(direction == Direction.UP){
            if(!upMinPriorityQueue.isEmpty()){
                destination = upMinPriorityQueue.poll();
            } else {
                if(!downMaxPriorityQueue.isEmpty()){
                    elevator.setDirection(Direction.DOWN);
                    destination = downMaxPriorityQueue.poll();
                } else {
                    elevator.setDirection(Direction.IDLE);
                }
            }
        } else if(direction == Direction.DOWN){
            if(!downMaxPriorityQueue.isEmpty()){
                destination = downMaxPriorityQueue.poll();
            } else {
                if(!upMinPriorityQueue.isEmpty()){
                    elevator.setDirection(Direction.UP);
                    destination = upMinPriorityQueue.poll();
                } else {
                    elevator.setDirection(Direction.IDLE);
                }
            }
        } else {
            if(!upMinPriorityQueue.isEmpty()){
                elevator.setDirection(Direction.UP);
                destination = upMinPriorityQueue.poll();
            } else if(!downMaxPriorityQueue.isEmpty()){
                elevator.setDirection(Direction.DOWN);
                destination = downMaxPriorityQueue.poll();
            }
        }

        if (destination == null) {
            moving = false;
        }
        return destination;
    }

    private void moveToFloor(int destinationFloor) {
        try {
            while (elevator.getCurrentFloor() != destinationFloor) {
                int currentFloor = elevator.getCurrentFloor();
                int nextFloor = currentFloor + (destinationFloor > currentFloor ? 1 : -1);

                Thread.sleep(1000);
                elevator.setCurrentFloor(nextFloor);
                System.out.println("Elevator " + elevator.getId() + " is now at floor " + nextFloor);
                serveStopIfNeeded(nextFloor);
            }
            System.out.println("Elevator " + elevator.getId() + " stopped at floor " + destinationFloor);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            synchronized (this) {
                moving = false;
            }
        }
    }

    private synchronized void serveStopIfNeeded(int floor) {
        Direction direction = elevator.getDirection();
        boolean shouldStop = false;

        if (direction == Direction.UP) {
            shouldStop = upMinPriorityQueue.remove(floor);
        } else if (direction == Direction.DOWN) {
            shouldStop = downMaxPriorityQueue.remove(floor);
        }

        if (shouldStop) {
            System.out.println("Elevator " + elevator.getId() + " stopped at floor " + floor);
        }
    }
}