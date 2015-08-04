package zarudnyi.trials.restaurant.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zarudnyi.trials.restaurant.config.AppConfig;
import zarudnyi.trials.restaurant.config.ApplicationContextProvider;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.OrderItem;
import zarudnyi.trials.restaurant.model.entity.User;
import zarudnyi.trials.restaurant.services.impl.GroupService;
import zarudnyi.trials.restaurant.services.impl.MenuService;
import zarudnyi.trials.restaurant.services.impl.OrderService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order/**")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MenuService menuService;


    private AppConfig.CurrentOrderHolder orderHolder(){
        return ApplicationContextProvider.getBean("orderHolder",AppConfig.CurrentOrderHolder.class );
    }

    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/item"}, method = {RequestMethod.POST})
    public String addItem(@RequestParam("item_id") Integer itemId) {
        JSONObject response = new JSONObject();

        try {
            Order currentOrder = orderHolder().getCurrentOrder();
            orderService.addOrderItem(currentOrder, menuService.getItemById(itemId));
            response.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("result", false);
        }

        return response.toString();
    }

    @RequestMapping(value = {"/item"}, method = {RequestMethod.DELETE})
    public String deleteItem(@RequestParam("item_id") Integer itemId) {
        JSONObject response = new JSONObject();

        try {
            Order currentOrder = orderHolder().getCurrentOrder();
            orderService.removeItemsByType(currentOrder, menuService.getItemById(itemId));
            response.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("result", false);
        }

        return response.toString();
    }

    @RequestMapping(value = {"/"}, method = {RequestMethod.DELETE})
    public String deleteOrder(@RequestParam("order_id") Integer orderId) {
        JSONArray response = new JSONArray();
        Order order = orderService.findById(orderId);

        if (order.getGroupId()!=null){
            Group group = groupService.findById(order.getGroupId());
            User owner = groupService.getOwner(group);
            if (owner.getId().equals(userService.currentUser().getId()))
                orderService.removeOrder(order);
        }

        return response.toString();
    }

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public String currentOrders() {
        JSONArray response = new JSONArray();
        List<Order> userInitiatedGroupOrders = orderService.getUserInitiatedGroupOrders(userService.currentUser());

        for (Order order : userInitiatedGroupOrders) {
            JSONObject orderJson = new JSONObject();
            orderJson.put("id", order.getId());
            orderJson.put("groupName", groupService.findById(order.getGroupId()).getName());
            response.put(orderJson);
        }

        return response.toString();
    }

    @RequestMapping(value = {"/item"}, method = {RequestMethod.GET})
    public List<OrderItem> orderItems(@RequestParam(value = "order_id", required = false) Integer orderId) {
        if (orderId == null)
            orderId = orderHolder().getCurrentOrder().getId();
        Order order = orderService.findById(orderId);

        return orderService.getOrderItems(order);
    }


}
