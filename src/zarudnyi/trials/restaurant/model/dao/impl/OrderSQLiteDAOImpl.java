package zarudnyi.trials.restaurant.model.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.OrderItemDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

@Repository("orderDao")
public class OrderSQLiteDAOImpl extends RestaurantAppSQLiteDao implements OrderDAO {

    @Autowired
    OrderItemDAO orderItemDAO;

    public Order createGroupOrder(User user, Group group) {

        Integer id = genericInsert("INSERT INTO orders (user_id, group_id,description,checkout_sum,status_id) VALUES (?,?,NULL ,NULL ,NULL )",user.getId(),group==null?null:group.getId());
        return findById(id);

    }

    public Order createOrder(User user) {
        return createGroupOrder(user, null);
    }

    public void removeOrder(Order order) {
        orderItemDAO.removeOrderItemsByOrder(order);
        jdbc.update("DELETE FROM orders WHERE id=?", order.getId());
    }

    public void updateOrder(Order order) {
        jdbc.update("INSERT OR REPLACE INTO orders (id, user_id, group_id, description, checkout_sum, status_id, order_date)" +
                "VALUES (?,?,?,?,?,?,?)", order.getId(),order.getUserId(), order.getGroupId(), order.getDescription(), order.getCheckOutSum(), order.getStatusId(),order.getOrderDate());
    }

    public Order findById(Integer orderId) {
        return jdbc.queryForObject("SELECT * FROM orders WHERE id=?", new Object[]{orderId}, new BeanPropertyRowMapper<Order>(Order.class));
    }

    public List<Order> findByUserId(Integer userId) {
        return jdbc.query("SELECT * FROM orders WHERE user_id=?", new Object[]{userId}, new BeanPropertyRowMapper<Order>(Order.class));
    }

    public List<Order> findByUserId(Integer userId, Integer statusId) {
        return jdbc.query("SELECT * FROM orders WHERE (user_id=? or group_id in (SELECT group_id FROM user_group WHERE user_id =? and option in (0,1)) ) and status_id=?", new Object[]{userId, userId,statusId}, new BeanPropertyRowMapper<Order>(Order.class));
    }

    public List<Order> findAll() {
        return jdbc.query("SELECT * FROM orders ", new Object[]{}, new BeanPropertyRowMapper<Order>(Order.class));
    }
}
