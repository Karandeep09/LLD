package service;

import entity.Ticket;
import java.time.Duration;

public class HourlyFee implements FeeService {
    private double hourlyRate;

    public HourlyFee(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateFee(Ticket ticket) {
        long parkedMinutes = Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toMinutes();
        long billableHours = Math.max(1, (long) Math.ceil(parkedMinutes / 60.0));
        return billableHours * hourlyRate;
    }
}
