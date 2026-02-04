package controllers;

import entities.ATM;
import entities.Transaction;
import entities.User;
import services.ATMService;
import services.UserService;
import utils.Constants;

import java.util.List;
import java.util.Scanner;

public class UserMenuController {

    private final User user;
    private final UserService userService;
    private final ATMService atmService;

    private final Scanner scanner = new Scanner(System.in);

    public UserMenuController(User user,
                              UserService userService,
                              ATMService atmService) {
        this.user = user;
        this.userService = userService;
        this.atmService = atmService;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== USER MENU ===");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. View Transactions");
            System.out.println("0. Logout");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> checkBalance();
                case "2" -> withdraw();
                case "3" -> deposit();
                case "4" -> viewTransactions();
                case "0" -> {
                    System.out.println("Logged out");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void checkBalance() {
        System.out.println(
                "Current balance: " +
                        Constants.CURRENCY_SYMBOL +
                        userService.checkBalance(user)
        );
    }

    private void withdraw() {
        ATM atm = atmService.loadATM();

        System.out.print("Enter amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (userService.withdraw(user, atm, amount)) {
            atmService.saveATM(atm);
            printReceipt(atm, "WITHDRAW", amount);
            System.out.println("Withdrawal successful");
        } else {
            System.out.println("Withdrawal failed");
        }
    }

    private void deposit() {
        ATM atm = atmService.loadATM();

        System.out.print("Enter amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (userService.deposit(user, amount)) {
            printReceipt(atm, "DEPOSIT", amount);
            System.out.println("Deposit successful");
        } else {
            System.out.println("Deposit failed");
        }
    }

    private void viewTransactions() {
        List<Transaction> transactions = userService.getTransactions(user);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n--- TRANSACTIONS ---");
        for (Transaction t : transactions) {
            System.out.println(
                    t.getType() + " " +
                            Constants.CURRENCY_SYMBOL + t.getAmount()
            );
        }
        System.out.println("--------------------");
    }

    private void printReceipt(ATM atm, String type, double amount) {
        if (!atmService.canPrintReceipt(atm)) {
            System.out.println("⚠ Unable to print receipt (no paper)");
            return;
        }

        atmService.usePaper(atm);

        System.out.println("\n--- RECEIPT ---");
        System.out.println("Type: " + type);
        System.out.println("Amount: " + Constants.CURRENCY_SYMBOL + amount);
        System.out.println("Balance: " + Constants.CURRENCY_SYMBOL + user.getBalance());
        System.out.println("----------------");
    }
}
