package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

public interface OrderDAO {

    Order createGroupOrder(User user, Group group);

    Order createOrder(User user);

    void removeOrder(Order order);

    void updateOrder(Order order);

    Order findById(Integer orderId);

    List<Order> findByUserId(Integer userId);

    List<Order> findAll();



}


