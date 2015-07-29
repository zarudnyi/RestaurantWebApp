package zarudnyi.trials.restaurant.model.dao.impl;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.entity.Order;

import java.sql.*;
import java.util.List;

@Repository("orderDao")
public class OrderSQLiteDAOImpl extends RestaurantAppSQLiteDao implements OrderDAO {


    public Order placeGroupOrder(Integer userId, Integer groupId) {

        Integer id = null;
        Connection conn = getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO orders (user_id, group_id,description,checkout_sum,status_id) VALUES (?,?,NULL ,NULL ,NULL )");
            preparedStatement.setInt(1,userId);
            if (groupId==null)
                preparedStatement.setNull(2, Types.INTEGER);
            else
                preparedStatement.setInt(2, groupId);

            preparedStatement.executeUpdate();

            ResultSet resultSet = conn.prepareStatement("SELECT last_insert_rowid()").executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        return findById(id);

    }

    public Order placeOrder(Integer userId) {
        return placeGroupOrder(userId, null);
    }

    public void removeOrder(Integer orderId) {
        jdbc.update("DELETE FROM orders WHERE id=?", orderId);
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
}
