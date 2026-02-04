package repositories;

import entities.ATM;

public interface ATMRepository {

    ATM loadATM();

    void saveATM(ATM atm);
}

