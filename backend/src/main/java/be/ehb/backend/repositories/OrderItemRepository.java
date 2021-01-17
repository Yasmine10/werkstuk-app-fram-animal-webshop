package be.ehb.backend.repositories;

import be.ehb.backend.entities.Order;
import be.ehb.backend.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    public List<OrderItem> findAllByOrder_Id(Long id);

    public Optional<OrderItem> findOrderItemByProduct_IdAndOrder_Id(Long productId, Long orderId);

}
