package entity;

import java.time.LocalDateTime;
import java.util.UUID;
import service.TicketService;
public class EntryGate {
    private int id;
    private TicketService ticketService;
    public EntryGate(int id, TicketService ticketService) {
        this.id = id;
        this.ticketService = ticketService;
    }
    public int getId() {
        return id;
    }
    public Ticket generateTicket(Vehicle vehicle, ParkingSpot spot, ParkingFloor floor) {
        if (spot != null) {
            return ticketService.createTicket(vehicle, spot, floor);
        }
        return null;
    }
}
