package zarudnyi.trials.restaurant.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import zarudnyi.trials.restaurant.controllers.MainController;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.services.impl.OrderService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@EnableWebMvc
@ComponentScan({ "zarudnyi.trials.restaurant.config", "zarudnyi.trials.restaurant.controllers","zarudnyi.trials.restaurant.model.dao.impl"  })
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MainController mainController(){
        return new MainController();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/*");
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setExposeContextBeansAsAttributes(true);

        return viewResolver;
    }


    @Bean
    @Scope("session")
    CurrentOrderHolder orderHolder(){
        return new CurrentOrderHolder();
    }




    public static class CurrentOrderHolder{

        @Autowired
        private UserService userService;

        @Autowired
        private OrderService orderService;

        private Order userOrder;

        private Order currentOrder;

        public Order getCurrentOrder() {
            return currentOrder;
        }
        public void setCurrentOrder(Order currentOrder) {
            this.currentOrder = currentOrder;
        }

        public Order getUserOrder() {
            return userOrder;
        }

        public Order setUserOrder(Order order) {
            return userOrder=order;
        }



        @PostConstruct
        public void init() throws Exception {
            if (orderService.getUserOrders(userService.currentUser(),OrderDAO.STATUS_INITIATED).isEmpty()){
                userOrder = orderService.placeOrder(userService.currentUser());
                userOrder.setStatusId(OrderDAO.STATUS_INITIATED);
                orderService.updateOrder(userOrder);
            }else {
                userOrder = orderService.getUserOrders(userService.currentUser(),OrderDAO.STATUS_INITIATED).get(0);
            }
            currentOrder = userOrder;


        }

        @PreDestroy
        public void removeUserOrder(){
            orderService.removeOrder(userOrder);
        }


    }

}