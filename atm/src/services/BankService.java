package services;

import entities.User;
import repositories.UserRepository;

import java.util.List;

public class BankService {

    private final UserRepository userRepository;

    public BankService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String cardNumber, String pin) {
        User user = userRepository.findByCardNumber(cardNumber);
        if (user != null && user.getPin().equals(pin)) {
            return user;
        }
        return null;
    }

    public Double getBalance(String cardNumber) {
        User user = userRepository.findByCardNumber(cardNumber);
        return user == null ? null : user.getBalance();
    }

    public boolean debit(String cardNumber, double amount) {
        if (amount <= 0) return false;
        User user = userRepository.findByCardNumber(cardNumber);
        if (user == null) return false;
        if (!user.withdraw(amount)) return false;
        saveUser(user);
        return true;
    }

    public boolean credit(String cardNumber, double amount) {
        if (amount <= 0) return false;
        User user = userRepository.findByCardNumber(cardNumber);
        if (user == null) return false;
        user.deposit(amount);
        saveUser(user);
        return true;
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
}
