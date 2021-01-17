package be.ehb.backend.repositories;

import be.ehb.backend.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    public List<Order> findAllByUser_Id(Long id);

}
