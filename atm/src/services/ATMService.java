package services;

import entities.ATM;
import repositories.ATMRepository;

public class ATMService {

    private final ATMRepository atmRepository;

    public ATMService(ATMRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public ATM loadATM() {
        return atmRepository.loadATM();
    }

    public void saveATM(ATM atm) {
        atmRepository.saveATM(atm);
    }

    public boolean canPrintReceipt(ATM atm) {
        return atm.hasPaper();
    }

    public void usePaper(ATM atm) {
        atm.usePaper();
        atmRepository.saveATM(atm);
    }
}
