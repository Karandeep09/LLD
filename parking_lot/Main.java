import entity.*;
import enums.*;
import java.util.*;
import service.*;

public class Main {
    public static void main(String[] args) {
        // Create ParkingLot instance
        ParkingLot parkingLot = new ParkingLot();
        TicketService ticketService = new TicketService();
        // Create Entry Gates
        EntryGate entryGate1 = new EntryGate(101, ticketService);
        EntryGate entryGate2 = new EntryGate(102, ticketService);
        parkingLot.addEntryGate(entryGate1);
        parkingLot.addEntryGate(entryGate2);

        // Create Exit Gates
        FeeService hourlyFee = new HourlyFee(20);
        PaymentService upiPayment = new UPIPayment();
        ExitGate exitGate1 = new ExitGate(201, upiPayment, hourlyFee);
        ExitGate exitGate2 = new ExitGate(202, upiPayment, hourlyFee);
        parkingLot.addExitGate(exitGate1);
        parkingLot.addExitGate(exitGate2);

        // Create ParkingFloors
        ParkingFloor floor1 = new ParkingFloor("F1", new HashMap<>());
        ParkingFloor floor2 = new ParkingFloor("F2", new HashMap<>());
        ParkingFloor floor3 = new ParkingFloor("F3", new HashMap<>());

        SpotAssignmentStrategy floorWiseStrategy = new FloorWiseSpotStrategy();
        floor1.addSpotManager(SpotType.CarSpot, new CarSpotManager(createSpots(5), floorWiseStrategy));
        floor2.addSpotManager(SpotType.CarSpot, new CarSpotManager(createSpots(5), floorWiseStrategy));
        floor3.addSpotManager(SpotType.CarSpot, new CarSpotManager(createSpots(5), floorWiseStrategy));
        
        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);
        parkingLot.addFloor(floor3);

        // Display ParkingLot Information
        System.out.println("===== Parking Lot Populated =====");
        System.out.println("Total Floors: " + parkingLot.getFloors().size());
        for (ParkingFloor floor : parkingLot.getFloors()) {
            System.out.println("  - " + floor.getFloor_no());
        }

        System.out.println("Total Entry Gates: " + parkingLot.getEntryGates().size());
        for (EntryGate gate : parkingLot.getEntryGates()) {
            System.out.println("  - Entry Gate ID: " + gate.getId());
        }

        System.out.println("Total Exit Gates: " + parkingLot.getExitGates().size());
        for (ExitGate gate : parkingLot.getExitGates()) {
            System.out.println("  - Exit Gate ID: " + gate.getId());
        }

        // Example: Generate a ticket at entry gate
        System.out.println("\n===== Testing Entry Gate Ticket Generation =====");
        Vehicle car = new Vehicle("DL-01-AB-1234", VehicleType.Car);
        Ticket ticket = parkingLot.reserveSpotForVehicle(car, entryGate1);
        // Ticket ticket = entryGate1.generateTicket(car);
        System.out.println("Ticket Generated!");
        System.out.println("Vehicle: " + car.getReg_no() + " (" + car.getType() + ")");
        System.out.println("Entry Time: " + ticket.getEntryTime());
        exitGate1.processExit(ticket);
    }

    private static List<ParkingSpot> createSpots(int count) {
        List<ParkingSpot> spots = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            spots.add(new ParkingSpot(UUID.randomUUID().toString()));
        }
        return spots;
    }
}
