package zarudnyi.trials.restaurant.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.OrderItemDAO;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.OrderItem;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderItemDAO orderItemDAO;

    public List<OrderItem> getUserActiveOrderItems(User user){
        List<Order> activeOrders = orderDAO.findByUserId(user.getId(), Order.STATUS_RECEIVED);

        for (Order order:activeOrders){
            if (!order.isGroupOrder())
                return orderItemDAO.findByOrder(order);
        }

        return new ArrayList<OrderItem>();
    }

}
