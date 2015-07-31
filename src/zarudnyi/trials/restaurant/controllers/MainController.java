package zarudnyi.trials.restaurant.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.SecurityConfig;
import zarudnyi.trials.restaurant.model.dao.GroupDAO;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.model.entity.MenuItem;
import zarudnyi.trials.restaurant.services.impl.MenuService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import java.util.*;

@Controller
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MainController {

    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    ModelAndView indexPage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("index");

        model.addObject("gallery", menuService.getGallery(4));

        List<MenuItem> dishes = menuService.getIndexPageDishes(6);

        model.addObject("firstDishes", dishes.subList(0,3));
        model.addObject("secondDishes", dishes.subList(3,6));


        return model;
    }

    @RequestMapping(value = {"/menu"}, method = {RequestMethod.GET})
    ModelAndView menuPage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("menu");

        List<MenuItem> allItems = menuService.getAllItems();

        Map<MenuCategory,List<MenuItem>> menu = new HashMap<MenuCategory, List<MenuItem>>();

        for (MenuItem item : allItems){
            if (!menu.containsKey(item.getCategory())){
                menu.put(item.getCategory(),new ArrayList<MenuItem>());
            }
            menu.get(item.getCategory()).add(item);
        }

        model.addObject("menu",menu);

        return model;
    }

    @RequestMapping(value = {"/gallery"}, method = {RequestMethod.GET})
    ModelAndView galleryPage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("gallery");
        model.addObject("gallery", menuService.getGallery(40));

        return model;
    }


    @Secured("ROLE_TEST")
    @RequestMapping(value = {"/profile"}, method = {RequestMethod.GET})
    ModelAndView profilePage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("profile");
        //    model.addObject("user", userService.getUserByLogin(currentUser()));


        return model;
    }

    private String currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


}
