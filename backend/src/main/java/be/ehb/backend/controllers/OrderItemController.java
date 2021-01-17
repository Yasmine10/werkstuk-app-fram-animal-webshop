package be.ehb.backend.controllers;

import be.ehb.backend.entities.Order;
import be.ehb.backend.entities.OrderItem;
import be.ehb.backend.repositories.OrderItemRepository;
import be.ehb.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/api/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    /*
     * GET method: /
     * to get all order items
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderItem> getAllOrderItems() {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItemRepository.findAll().forEach(orderItems::add);
        return orderItems;
    }

    /*
     * GET method: /{id}
     * to get a specific order item by id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<OrderItem> getOrderItemById(@PathVariable(value = "id") Long id) {
        return orderItemRepository.findById(id);
    }

    /*
     * GET method: /order={id}
     * to get all order items for a specific order
     */
    @RequestMapping(value = "/order={id}", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderItem> getAllOrderItemsByOrderId(@PathVariable(value = "id") Long id) {
        // check if the order exists
        Optional<Order> existingOrder = orderRepository.findById(id);
        if(existingOrder.isPresent()) {
            return new ArrayList<>(orderItemRepository.findAllByOrder_Id(id));
        }
        return null;
    }

    /*
     * POST method: /
     * to save a new order item
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrderItem(@Valid @RequestBody OrderItem orderItem) {
        try {
            // check if order item already exists
            Optional<OrderItem> existingOrderItem = orderItemRepository.findOrderItemByProduct_IdAndOrder_Id(orderItem.getProduct().getId(), orderItem.getOrder().getId());
            if(existingOrderItem.isPresent()) {
                throw new Exception("Order item already exists.");
            } else {
                // check if order exists
                Optional<Order> existingOrder = orderRepository.findById(orderItem.getOrder().getId());
                if(existingOrder.isPresent()) {
                    orderItemRepository.save(orderItem);
                } else {
                    throw new Exception("The order for this order item doesn't exist.");
                }
            }
        } catch (Exception e) {
            return "Error saving the new order item: " + e.toString();
        }
        return "Order item successfully created!";
    }

    /*
     * PUT method: /
     * to update a specific order item
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public String updateOrderItem(@Valid @RequestBody OrderItem orderItem) {
        try {
            // check if the order item exists
            Optional<OrderItem> existingOrderItem = orderItemRepository.findById(orderItem.getId());
            if(existingOrderItem.isPresent()) {
                orderItemRepository.save(orderItem);
            } else {
                throw new Exception("Order item doesn't exist.");
            }
        } catch (Exception e) {
            return "Error updating the order item: " + e.toString();
        }
        return "Order item successfully updated!";
    }

    /*
     * DELETE method: /{id}
     * to delete a specific order item
     */

}
