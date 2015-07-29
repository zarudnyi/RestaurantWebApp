package zarudnyi.trials.restaurant.model.dao.impl;


import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppDao;
import zarudnyi.trials.restaurant.model.entity.Order;

@Repository("orderDao")
public class OrderDAOImpl extends RestaurantAppDao implements OrderDAO {


    public Order placeGroupOrder(int userId, int groupId) {
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
