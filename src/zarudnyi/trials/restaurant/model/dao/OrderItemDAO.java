package zarudnyi.trials.restaurant.model.dao;

import zarudnyi.trials.restaurant.model.entity.MenuItem;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.OrderItem;

import java.util.List;


public interface OrderItemDAO {

    OrderItem createOrderItem (Order order, MenuItem menuItem);

    List<OrderItem> findByOrder (Order order);

    List<OrderItem> findAll();

    OrderItem findById(Integer id);

    void updateOrderItem (OrderItem orderItem);

    void removeOrderItem(OrderItem orderItem);


}
