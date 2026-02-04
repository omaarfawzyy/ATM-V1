package entities;

import java.time.LocalDateTime;

public class Transaction {

    private String cardNumber;
    private String type; // WITHDRAW or DEPOSIT
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(String cardNumber, String type, double amount, LocalDateTime dateTime) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
