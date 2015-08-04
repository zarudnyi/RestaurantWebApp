package zarudnyi.trials.restaurant.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zarudnyi.trials.restaurant.config.AppConfig;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.OrderItemDAO;
import zarudnyi.trials.restaurant.model.entity.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderItemDAO orderItemDAO;



    public Order placeOrder(User user){
        return orderDAO.createOrder(user);
    }

    public Order placeGroupOrder(User owner,Group group){
        Order groupOrder = orderDAO.createGroupOrder(owner, group);
        groupOrder.setStatusId(OrderDAO.STATUS_INITIATED);
        orderDAO.updateOrder(groupOrder);
        return groupOrder;
    }

    public void removeOrder(Order order){
        orderDAO.removeOrder(order);
    }

    public List<Order> getUserOrders(User user){
        return orderDAO.findByUserId(user.getId());
    }
    public List<Order> getUserOrders(User user, Integer statusId){
        return orderDAO.findByUserId(user.getId(),statusId);
    }


    public void removeItemsByType(Order order, User user, MenuItem type) {
        List<OrderItem> orderItems = orderItemDAO.findByOrderAndOwner(order, user);
        for (OrderItem orderItem:orderItems){
            if (orderItem.getMenuItemId().equals(type.getId()))
                orderItemDAO.removeOrderItem(orderItem);
        }

    }

    public Order findById (Integer id){
        return orderDAO.findById(id);
    }

    public List<OrderItem> getOrderItems(Order order) {
        return orderItemDAO.findByOrder(order);
    }

    public List<Order> getUserInitiatedGroupOrders(User user) {
        List<Order> orders = orderDAO.findByUserId(user.getId() , OrderDAO.STATUS_INITIATED);
        List<Order> res = new ArrayList<Order>();

        for (Order o : orders){
            if (o.getGroupId()!=null)
                res.add(o);
        }

        return res;

    }

    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public void addOrderItem(Order order, User user, MenuItem menuItem) {
        orderItemDAO.createOrderItem(order,user, menuItem);
    }
}
