package services;

import entities.ATM;
import repositories.ATMRepository;

public class TechnicianService {

    private final ATMRepository atmRepository;

    public TechnicianService(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public ATM viewATMStatus() {
        return atmRepository.loadATM();
    }
}
