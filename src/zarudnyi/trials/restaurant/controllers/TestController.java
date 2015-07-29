package zarudnyi.trials.restaurant.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.AppConfig;
import zarudnyi.trials.restaurant.config.ApplicationContextProvider;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.impl.OrderSQLiteDAOImpl;

@Controller
public class TestController {

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Hello World!");
        model.setViewName("helloworld");
        AppConfig.resetSchema();

        ApplicationContext appContext = ApplicationContextProvider.getApplicationContext();
        OrderDAO orderDao = appContext.getBean("orderDao", OrderSQLiteDAOImpl.class);
        Integer id = orderDao.placeOrder(1).getId();
        id = orderDao.placeOrder(1).getId();



        return model;
    }
}
