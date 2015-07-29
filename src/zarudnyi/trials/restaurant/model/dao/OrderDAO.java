package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.Order;

import java.util.List;

public interface OrderDAO {

    Order placeGroupOrder(Integer userId, Integer groupId);

    Order placeOrder(Integer userId);

    void removeOrder(Integer orderId);

    void updateOrder(Order order);

    Order findById(Integer orderId);

    List<Order> findByUserId(Integer userId);

}


