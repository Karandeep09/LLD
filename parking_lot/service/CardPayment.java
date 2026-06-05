package service;

public class CardPayment implements PaymentService {
    @Override
    public boolean processPayment(double amount) {
        // Simulate card payment processing logic
        System.out.println("Processing card payment of amount: " + amount);
        // For simplicity, we assume the payment is always successful
        return true;
    }
}