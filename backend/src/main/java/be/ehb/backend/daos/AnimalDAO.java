package be.ehb.backend.daos;

import be.ehb.backend.entities.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalDAO extends CrudRepository<Animal, Long> {



}
