package service;

import java.time.LocalDateTime;
import java.util.UUID;
import entity.*;

public class TicketService {
    public TicketService() {
    }
    public Ticket createTicket(Vehicle vehicle, ParkingSpot spot, ParkingFloor floor) {
        if (spot != null) {
            return new Ticket(UUID.randomUUID().toString(), LocalDateTime.now(), vehicle, spot, floor);
        }
        return null;
    }
}