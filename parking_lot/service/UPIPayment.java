package service;

public class UPIPayment implements PaymentService {
    @Override
    public boolean processPayment(double amount) {
        // Simulate UPI payment processing logic
        System.out.println("Processing UPI payment of amount: " + amount);
        // For simplicity, we assume the payment is always successful
        return true;
    }
}