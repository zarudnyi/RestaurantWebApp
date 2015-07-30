package zarudnyi.trials.restaurant.model.dao.impl;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

@Repository("orderDao")
public class OrderSQLiteDAOImpl extends RestaurantAppSQLiteDao implements OrderDAO {


    public Order createGroupOrder(User user, Group group) {

        Integer id = genericInsert("INSERT INTO orders (user_id, group_id,description,checkout_sum,status_id) VALUES (?,?,NULL ,NULL ,NULL )",user.getId(),group==null?null:group.getId());
        return findById(id);

    }

    public Order createOrder(User user) {
        return createGroupOrder(user, null);
    }

    public void removeOrder(Order order) {
        jdbc.update("DELETE FROM orders WHERE id=?", order.getId());
    }

    public void updateOrder(Order order) {
        jdbc.update("INSERT OR REPLACE INTO orders (id, user_id, group_id, description, checkout_sum, status_id, order_date)" +
                "VALUES (?,?,?,?,?,?,?)", order.getId(), order.getGroupId(), order.getDescription(), order.getCheckOutSum(), order.getStatusId(),order.getOrderDate());
    }

    public Order findById(Integer orderId) {
        return jdbc.queryForObject("SELECT * FROM orders WHERE id=?", new Object[]{orderId}, new BeanPropertyRowMapper<Order>(Order.class));
    }

    public List<Order> findByUserId(Integer userId) {
        return jdbc.query("SELECT * FROM orders WHERE user_id=?", new Object[]{userId}, new BeanPropertyRowMapper<Order>(Order.class));
    }

    public List<Order> findAll() {
        return jdbc.query("SELECT * FROM orders ", new Object[]{}, new BeanPropertyRowMapper<Order>(Order.class));
    }
}
