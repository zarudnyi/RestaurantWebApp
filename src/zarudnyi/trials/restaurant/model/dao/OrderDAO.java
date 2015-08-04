package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

public interface OrderDAO {
    Integer STATUS_INITIATED = 0;
    Integer STATUS_RECEIVED = 1;
    Integer STATUS_IN_PROGRESS = 2;
    Integer STATUS_COMPLETED = 3;

    Order createGroupOrder(User user, Group group);

    Order createOrder(User user);

    void removeOrder(Order order);

    void updateOrder(Order order);

    Order findById(Integer orderId);

    List<Order> findByUserId(Integer userId);

    List<Order> findByUserId(Integer userId, Integer statusId);

    List<Order> findAll();





}


