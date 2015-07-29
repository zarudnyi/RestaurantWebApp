package zarudnyi.trials.restaurant.model.services.impl;


import org.springframework.jdbc.core.support.JdbcDaoSupport;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.services.OrderManager;

public class OrderManagerImpl  implements OrderManager {


    public Order placeGroupOrder(int groupId) {
        return null;
    }

    public Order placeOrder(int userId) {
        return null;
    }

    public void removeOrder(int orderId) {

    }

    public void updateOrder(Order order) {

    }

    public Order findById(int orderId) {
        return null;
    }

    public Order findByUserId(int userId) {
        return null;
    }
}
