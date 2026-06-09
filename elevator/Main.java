import entity.Building;
public class Main {
    public static void main(String[] args) {
        // Create a building with 10 floors and 3 elevators
        Building building = new Building(10, 3);
        building.getFloors().get(1).getUpButton().press(); // Request an elevator to go up from the ground floor
        building.getFloors().get(4).getDownButton().press(); // Request an elevator to go down from the 5th floor
    }
}