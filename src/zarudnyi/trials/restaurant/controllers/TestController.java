package zarudnyi.trials.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.AppConfig;
import zarudnyi.trials.restaurant.config.ApplicationContextProvider;
import zarudnyi.trials.restaurant.config.SecurityConfig;
import zarudnyi.trials.restaurant.model.dao.GroupDAO;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.dao.impl.OrderSQLiteDAOImpl;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GroupDAO groupDAO;

    @Secured({SecurityConfig.ROLE_ADMIN} )
    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Hello World!");
        model.setViewName("helloworld");
        AppConfig.resetSchema();


        List<User> users = userDAO.findAll();

        Order order = orderDAO.createOrder(users.get(0));




        return model;
    }
}
