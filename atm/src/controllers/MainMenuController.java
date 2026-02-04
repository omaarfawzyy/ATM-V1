package controllers;

import services.AuthenticationService;
import services.ATMService;
import services.TechnicianService;
import services.UserService;

import entities.User;
import entities.Technician;

import java.util.Scanner;

public class MainMenuController {

    private final AuthenticationService authService;
    private final UserService userService;
    private final ATMService atmService;
    private final TechnicianService technicianService;

    private final Scanner scanner = new Scanner(System.in);

    public MainMenuController(AuthenticationService authService,
                              UserService userService,
                              ATMService atmService,
                              TechnicianService technicianService) {
        this.authService = authService;
        this.userService = userService;
        this.atmService = atmService;
        this.technicianService = technicianService;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== ATM SYSTEM ===");
            System.out.println("1. User Login");
            System.out.println("2. Technician Login");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> userLogin();
                case "2" -> technicianLogin();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void userLogin() {
        System.out.print("Card Number: ");
        String card = scanner.nextLine();

        System.out.print("PIN: ");
        String pin = scanner.nextLine();

        User user = authService.authenticateUser(card, pin);
        if (user == null) {
            System.out.println("Invalid card or PIN");
            return;
        }

        new UserMenuController(user, userService, atmService).start();
    }

    private void technicianLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        Technician tech = authService.authenticateTechnician(username, password);
        if (tech == null) {
            System.out.println("Invalid technician credentials");
            return;
        }

        new TechnicianMenuController(technicianService).start();
    }
}
