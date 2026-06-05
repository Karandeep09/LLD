package service;
import entity.Ticket;
public interface PaymentService {
    boolean processPayment(double amount);
}