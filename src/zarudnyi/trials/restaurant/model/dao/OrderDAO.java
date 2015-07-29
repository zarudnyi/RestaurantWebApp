package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.Order;

public interface OrderDAO {

    Order placeGroupOrder(int userId, int groupId);

    Order placeOrder(int userId);

    void removeOrder(int orderId);

    void updateOrder(Order order);

    Order findById(int orderId);

    Order findByUserId(int userId);

}


