package be.ehb.backend.daos;

import be.ehb.backend.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, Long> {



}
