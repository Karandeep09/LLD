package service;
import java.util.Collections;
import java.util.PriorityQueue;
import enums.Direction;
import entity.Elevator;

public class ElevatorController {
   private PriorityQueue<Integer> downMaxPriorityQueue;
   private PriorityQueue<Integer> upMinPriorityQueue;
   private Elevator elevator;
   private int currentFloor;
   private Direction direction;
   public ElevatorController(Elevator elevator) {
        downMaxPriorityQueue = new PriorityQueue<>(Collections.reverseOrder());
        upMinPriorityQueue = new PriorityQueue<>();
        this.elevator = elevator;
        this.currentFloor = elevator.getCurrentFloor();
        this.direction = elevator.getDirection();
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
    public void addDestination(int floor){
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
        move();
    }
    private void move() {
        if(direction == Direction.UP){
            if(!upMinPriorityQueue.isEmpty()){
                currentFloor = upMinPriorityQueue.poll();
            } else {
                if(!downMaxPriorityQueue.isEmpty()){
                    direction = Direction.DOWN;
                    currentFloor = downMaxPriorityQueue.poll();
                } else {
                    direction = Direction.IDLE;
                }
            }
        } else if(direction == Direction.DOWN){
            if(!downMaxPriorityQueue.isEmpty()){
                currentFloor = downMaxPriorityQueue.poll();
            } else {
                if(!upMinPriorityQueue.isEmpty()){
                    direction = Direction.UP;
                    currentFloor = upMinPriorityQueue.poll();
                } else {
                    direction = Direction.IDLE;
                }
            }
        } else {
            if(!upMinPriorityQueue.isEmpty()){
                direction = Direction.UP;
                currentFloor = upMinPriorityQueue.poll();
            } else if(!downMaxPriorityQueue.isEmpty()){
                direction = Direction.DOWN;
                currentFloor = downMaxPriorityQueue.poll();
            }
        }
        elevator.setDirection(direction);
        elevator.setCurrentFloor(currentFloor);
    }
}