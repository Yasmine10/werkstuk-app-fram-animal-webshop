package be.ehb.backend.repositories;

import be.ehb.backend.entities.Animal;
import be.ehb.backend.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    public Optional<Category> findByNameAndAnimalId(String name, Long id);

    public List<Category> findAllByAnimal(Animal animal);

}
