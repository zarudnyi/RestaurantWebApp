package zarudnyi.trials.restaurant.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.WebConfig;
import zarudnyi.trials.restaurant.model.entity.*;
import zarudnyi.trials.restaurant.services.impl.GroupService;
import zarudnyi.trials.restaurant.services.impl.MenuService;
import zarudnyi.trials.restaurant.services.impl.OrderService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api/order/**")
@Scope("request")
public class OrderController {
    @Autowired
    private WebConfig.CurrentOrderHolder orderHolder;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MenuService menuService;


    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/item"}, method = {RequestMethod.GET})
    public String orderItems(@RequestParam(value = "order_id") Integer orderId) {
        if (orderId == null)
            orderId = orderHolder.getCurrentOrder().getId();
        Order order = orderService.findById(orderId);

        JSONArray response = new JSONArray();

        List<OrderItem> items = orderService.getOrderItems(order);

        Map<User,List<OrderItemResponse>> data = new HashMap<User, List<OrderItemResponse>>();

        for (OrderItem orderItem : items) {
            User user = userService.getUserById(orderItem.getUserId());
            if (!data.containsKey(user))
                data.put(user,new ArrayList<OrderItemResponse>());

            OrderItemResponse orderItemResponse = new OrderItemResponse();

            MenuItem menuItem = menuService.getItemById(orderItem.getMenuItemId());

            orderItemResponse.menuItemId=menuItem.getId();
            orderItemResponse.name = menuItem.getName();
            orderItemResponse.price = menuItem.getPrice();
            orderItemResponse.increaseQty();

            List<OrderItemResponse> list = data.get(user);

            if (list.contains(orderItemResponse)){
                list.get(list.indexOf(orderItemResponse)).increaseQty();
            }else {
                list.add(orderItemResponse);
            }
        }

        for (User user : data.keySet()) {
            JSONObject userJson = new JSONObject();
            userJson.put("login", user.getLogin());
            userJson.put("fname", user.getFname());
            userJson.put("lname", user.getLname());
            userJson.put("id", user.getId());

            JSONArray itemsJson = new JSONArray();

            for (OrderItemResponse orderItem: data.get(user)){
                JSONObject itemJson = new JSONObject();

                itemJson.put("itemId", orderItem.menuItemId);
                itemJson.put("itemName", orderItem.name);
                itemJson.put("price", orderItem.price);
                itemJson.put("qty", orderItem.qty);

                itemsJson.put(itemJson);
            }
            userJson.put("items", itemsJson);
            response.put(userJson);
        }

        return response.toString();
    }


    @RequestMapping(value = {"/item"}, method = {RequestMethod.POST})
    public String addItem(@RequestParam("item_id") Integer itemId) {
        JSONObject response = new JSONObject();

        try {
            Order currentOrder = orderHolder.getCurrentOrder();

            orderService.addOrderItem(currentOrder, userService.currentUser(), menuService.getItemById(itemId));
            response.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("result", false);
        }

        return response.toString();
    }

    @RequestMapping(value = {"/item"}, method = {RequestMethod.DELETE})
    public String deleteItem(@RequestParam("item_id") Integer itemId, @RequestParam("order_id") Integer orderId) {
        JSONObject response = new JSONObject();
        try {
            orderService.removeItemsByType(orderService.findById(orderId), userService.currentUser(), menuService.getItemById(itemId));
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

        if (order.getGroupId() != null) {
            Group group = groupService.findById(order.getGroupId());
            User owner = groupService.getOwner(group);
            if (owner.getId().equals(userService.currentUser().getId()))
                orderService.removeOrder(order);
        }

        return response.toString();
    }
    @RequestMapping(value = {"/"}, method = {RequestMethod.POST})
    public String setCurrent(@RequestParam("order_id") Integer orderId) {
        orderHolder.setCurrentOrder(orderService.findById(orderId));
        return "";
    }

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public String currentOrders() {
        JSONArray response = new JSONArray();
        User currentUser = userService.currentUser();
        List<Order> userInitiatedGroupOrders = orderService.getUserInitiatedGroupOrders(currentUser);
        JSONObject orderJson = new JSONObject();
        orderJson.put("id", orderHolder.getUserOrder().getId());
        orderJson.put("name", "My Order");
        orderJson.put("isOwner", true);
        orderJson.put("isCurrent", orderHolder.getCurrentOrder().equals(orderHolder.getUserOrder()));
        orderJson.put("isGroup", false);
        response.put(orderJson);

        for (Order order : userInitiatedGroupOrders) {
            orderJson = new JSONObject();
            orderJson.put("id", order.getId());
            Group group = groupService.findById(order.getGroupId());
            orderJson.put("name", group.getName() + " Order");
            orderJson.put("isOwner", groupService.getOwner(group).getId().equals(currentUser.getId()));
            orderJson.put("isCurrent", orderHolder.getCurrentOrder().equals(order));
            orderJson.put("isGroup", true);


            response.put(orderJson);
        }


        return response.toString();
    }

    @RequestMapping(value = {"/checkOut"}, method = {RequestMethod.POST})
    public String checkOut(@RequestParam("date") String date, @RequestParam("order_id") Integer orderId, HttpServletResponse response) {


        try {

            SimpleDateFormat parserSDF=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Order order = orderService.findById(orderId);

            order.setOrderDate(parserSDF.parse(date)  );
            orderService.checkOut(order);

            if (orderId.equals(orderHolder.getUserOrder().getId())){
                orderHolder.newUserOrder();
            }
            response.sendRedirect("/profile");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return "";


        }
    }

    private static class OrderItemResponse{
        Integer qty=0;
        Integer menuItemId;
        String name;
        Integer price;

        public void increaseQty(){
            qty+=1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OrderItemResponse that = (OrderItemResponse) o;

            return !(menuItemId != null ? !menuItemId.equals(that.menuItemId) : that.menuItemId != null);

        }

        @Override
        public int hashCode() {
            return menuItemId != null ? menuItemId.hashCode() : 0;
        }
    }


}
