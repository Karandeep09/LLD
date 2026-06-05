package entity; 

import service.FeeService;
import service.PaymentService;
import java.time.LocalDateTime;

public class ExitGate {
    private int id;
    private PaymentService paymentService; 
    private FeeService feeService;
    
    public ExitGate(int id, PaymentService paymentService, FeeService feeService) {
        this.id = id;
        this.paymentService = paymentService;
        this.feeService = feeService;
    }

    public int getId() {
        return id;
    }

    public void processExit(Ticket ticket) {
        ticket.setExitTime(LocalDateTime.now());
        double fee = feeService.calculateFee(ticket);
        ticket.setFee(fee);
        System.out.println("Payment of $" + fee + " processed for Ticket ID: " + ticket.getId());
        paymentService.processPayment(fee);
        ticket.getParkingFloor().getSpotManager().get(ticket.getVehicle().getType().toSpotType()).releaseSpot(ticket.getParkingSpot());
    }
}
