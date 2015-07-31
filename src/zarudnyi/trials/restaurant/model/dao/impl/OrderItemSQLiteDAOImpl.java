package zarudnyi.trials.restaurant.model.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.OrderItemDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.entity.MenuItem;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.OrderItem;

import java.util.List;

@Repository("orderItemDao")
public class OrderItemSQLiteDAOImpl extends RestaurantAppSQLiteDao implements OrderItemDAO {
    public OrderItem createOrderItem(Order order, MenuItem menuItem) {
        Integer id = genericInsert("INSERT INTO order_items (order_id, menu_id, description) VALUES (NULL ,NULL ,NULL ) ");
        return findById(id);
    }

    public List<OrderItem> findByOrder(Order order) {
        return jdbc.query("select * from order_items where order_id=?",new Object[]{order.getId()},new BeanPropertyRowMapper<OrderItem>(OrderItem.class));
    }

    public List<OrderItem> findAll() {
        return jdbc.query("select * from order_items",new BeanPropertyRowMapper<OrderItem>(OrderItem.class));
    }

    public OrderItem findById(Integer id) {
        return jdbc.queryForObject("select * from order_items where order_id=?",new Object[]{id},new BeanPropertyRowMapper<OrderItem>(OrderItem.class));
    }

    public void updateOrderItem(OrderItem orderItem) {
        jdbc.update("INSERT or replace INTO order_items (id, order_id, menu_item_id, description) VALUES (?,?,?,?)",orderItem.getId(),orderItem.getOrderId(),orderItem.getMenuItemId(),orderItem.getDescription());
    }

    public void removeOrderItem(OrderItem orderItem) {
        jdbc.update("DELETE FROM order_items WHERE id=?",orderItem.getId());
    }
}
