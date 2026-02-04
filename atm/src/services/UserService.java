package services;

import entities.ATM;
import entities.Transaction;
import entities.User;
import repositories.TransactionRepository;
import repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository,
                       TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public double checkBalance(User user) {
        return user.getBalance();
    }

    public boolean deposit(User user, double amount) {
        if (amount <= 0) return false;

        user.deposit(amount);
        saveUser(user);

        transactionRepository.save(
                new Transaction(user.getCardNumber(),
                        "DEPOSIT",
                        amount,
                        LocalDateTime.now())
        );
        return true;
    }

    public boolean withdraw(User user, ATM atm, double amount) {
        if (amount <= 0 || user.getBalance() < amount) return false;

        if (!canDispenseCash(atm.getCash(), amount)) {
            return false;
        }

        dispenseCash(atm.getCash(), amount);
        user.withdraw(amount);

        saveUser(user);

        transactionRepository.save(
                new Transaction(user.getCardNumber(),
                        "WITHDRAW",
                        amount,
                        LocalDateTime.now())
        );
        return true;
    }

    public List<Transaction> getTransactions(User user) {
        List<Transaction> all = transactionRepository.findAll();
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : all) {
            if (t.getCardNumber().equals(user.getCardNumber())) {
                result.add(t);
            }
        }
        return result;
    }

    private void saveUser(User updatedUser) {
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCardNumber().equals(updatedUser.getCardNumber())) {
                users.set(i, updatedUser);
                break;
            }
        }
        userRepository.saveAll(users);
    }

    // ---------------- CASH LOGIC ----------------

    private boolean canDispenseCash(Map<Integer, Integer> cash, double amount) {
        int remaining = (int) amount;

        for (int denom : new int[]{100, 50, 20, 10}) {
            int available = cash.getOrDefault(denom, 0);
            int needed = remaining / denom;
            int used = Math.min(available, needed);
            remaining -= used * denom;
        }
        return remaining == 0;
    }

    private void dispenseCash(Map<Integer, Integer> cash, double amount) {
        int remaining = (int) amount;

        for (int denom : new int[]{100, 50, 20, 10}) {
            int available = cash.getOrDefault(denom, 0);
            int needed = remaining / denom;
            int used = Math.min(available, needed);

            cash.put(denom, available - used);
            remaining -= used * denom;
        }
    }
}
