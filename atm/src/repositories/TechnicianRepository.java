package repositories;

import entities.Technician;

import java.util.List;

public interface TechnicianRepository {

    List<Technician> findAll();

    Technician findByUsername(String username);
}
