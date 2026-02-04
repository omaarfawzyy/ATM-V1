package unit;

import entities.User;

public class UserDepositTest {

    public static void main(String[] args) {
        User user = new User("1", "0000", 10.0);
        user.deposit(5.0);
        assertEquals(15.0, user.getBalance(), "deposit increases balance");
        System.out.println("UserDepositTest: Test passed");
    }

    private static void assertEquals(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.0001) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
}
