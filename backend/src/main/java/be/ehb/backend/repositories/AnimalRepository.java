package be.ehb.backend.repositories;

import be.ehb.backend.entities.Animal;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    public Optional<Animal> findAnimalByName(String name);

}