package be.ehb.backend.daos;

import be.ehb.backend.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDAO extends CrudRepository<Product, Long> {



}
