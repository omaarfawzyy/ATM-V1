package repositories;

import entities.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findByCardNumber(String cardNumber);

    void saveAll(List<User> users);
}
