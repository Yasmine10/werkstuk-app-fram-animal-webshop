package be.ehb.backend.controllers;

import be.ehb.backend.entities.Order;
import be.ehb.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    /*
     * GET method: /
     * to get all orders
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> getAllOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    /*
     * GET method: /{id}
     * to get a specific order
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Order> getOrderById(@PathVariable(value = "id") Long id){
        return orderRepository.findById(id);
    }

    /*
     * GET method: /user={id}
     * to get all the orders for a specific user
     */
    @RequestMapping(value = "/user={id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> getAllOrdersByUserId(@PathVariable(value = "id") Long id){
        return new ArrayList<>(orderRepository.findAllByUser_Id(id));
    }

    /*
     * POST method: /
     * to save a new order
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Order saveOrder(@Valid @RequestBody Order order) throws Exception{

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        order.setOrderDate(localDate);

        return orderRepository.save(order);
    }

    /*
     * PUT method: /
     * to update an existing order
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public String updateOrder(@Valid @RequestBody Order order) {
        try {
            // check if the order exists
            Optional<Order> existingOrder = orderRepository.findById(order.getId());
            if(existingOrder.isPresent()) {
                orderRepository.save(order);
            } else {
                throw new Exception("The order doesn't exist.");
            }
        } catch (Exception e) {
            return "Error updating the order: " + e.toString();
        }
        return "Order successfully updated!";
    }

    /*
     * DELETE method: /
     * you can't delete an order, because it's not possible to cancel an order yet
     */

}
