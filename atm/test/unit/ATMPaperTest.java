package unit;

import entities.ATM;

import java.util.HashMap;

public class ATMPaperTest {

    public static void main(String[] args) {
        ATM atm = new ATM(new HashMap<>(), 1, 100, "1.0.0");
        atm.usePaper();
        assertEquals(0, atm.getPaper(), "paper decrements");
        System.out.println("ATMPaperTest: Test passed");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
}
