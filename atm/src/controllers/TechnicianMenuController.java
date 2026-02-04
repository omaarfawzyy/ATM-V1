package controllers;

import entities.ATM;
import services.TechnicianService;

public class TechnicianMenuController {

    private final TechnicianService technicianService;

    public TechnicianMenuController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    public void start() {
        ATM atm = technicianService.viewATMStatus();

        System.out.println("\n=== ATM STATUS ===");
        System.out.println("Firmware: " + atm.getFirmwareVersion());
        System.out.println("Paper remaining: " + atm.getPaper());
        System.out.println("Ink remaining: " + atm.getInk());
        System.out.println("Cash available:");

        atm.getCash().forEach((denom, qty) ->
                System.out.println("  " + denom + " x " + qty)
        );

        System.out.println("\nPress Enter to return...");
        try {
            System.in.read();
        } catch (Exception ignored) {}
    }
}
