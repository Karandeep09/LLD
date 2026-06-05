package service;

import entity.Ticket;

public interface FeeService {
    double calculateFee(Ticket ticket);
}
