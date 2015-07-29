package zarudnyi.trials.restaurant.model.services;


import zarudnyi.trials.restaurant.model.entity.Order;

public interface OrderManager {

    Order placeGroupOrder (int groupId);
    Order placeOrder (int userId);
    void removeOrder (int orderId);
    void updateOrder (Order order);
    Order findById (int orderId);
    Order findByUserId (int userId);


}
