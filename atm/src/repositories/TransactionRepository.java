package repositories;

import entities.Transaction;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> findAll();

    void save(Transaction transaction);
}
