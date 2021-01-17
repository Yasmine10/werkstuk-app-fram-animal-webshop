package be.ehb.backend.repositories;

import be.ehb.backend.entities.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    public List<Product> findAllByAnimal_Id(Long id);

    public List<Product> findAllByCategory_Id(Long id);

    public List<Product> findAllByAnimal_Name(String name);

    public Optional<Product> findProductByNameAndBrandAndAndAnimal_IdAndCategory_Id(String name, String brand, Long animalId, Long categoryId);

    public List<Product> findAllByBrandAndAnimal_Name(String brand, String name);

    @Query("select distinct new Product(p.brand) from Product p where p.animal.name = ?1")
    public List<Product> findAllBrands(String name);
}
