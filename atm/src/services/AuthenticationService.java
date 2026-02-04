package services;

import entities.Technician;
import entities.User;
import repositories.TechnicianRepository;
import repositories.UserRepository;

public class AuthenticationService {

    private final UserRepository userRepository;
    private final TechnicianRepository technicianRepository;

    public AuthenticationService(UserRepository userRepository,
                                 TechnicianRepository technicianRepository) {
        this.userRepository = userRepository;
        this.technicianRepository = technicianRepository;
    }

    public User authenticateUser(String cardNumber, String pin) {
        User user = userRepository.findByCardNumber(cardNumber);
        if (user != null && user.getPin().equals(pin)) {
            return user;
        }
        return null;
    }

    public Technician authenticateTechnician(String username, String password) {
        Technician tech = technicianRepository.findByUsername(username);
        if (tech != null && tech.authenticate(password)) {
            return tech;
        }
        return null;
    }
}
