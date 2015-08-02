package zarudnyi.trials.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.config.AppConfig;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.User;
import zarudnyi.trials.restaurant.services.impl.UserService;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public ModelAndView loginPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = {"/profile"}, method = {RequestMethod.GET})
    ModelAndView profilePage(){
        ModelAndView model = new ModelAndView();

        model.setViewName("profile");
        model.addObject("user", userService.getUserByLogin(currentUser()));


        return model;
    }

    @RequestMapping(value = {"/updateUser"}, method = {RequestMethod.POST})
    public ModelAndView updateUser(@RequestParam("fname") String fname,
                                   @RequestParam("lname") String lname,
                                   @RequestParam("password") String password) {
        ModelAndView model = new ModelAndView();
        model.setViewName("profile");
        User curUser = userService.getUserByLogin(currentUser());

        curUser.setFname(fname);
        curUser.setLname(lname);
        if (password!=null && !password.isEmpty())
            curUser.setPassword(password);

        userService.updateUser(curUser);

        return model;
    }

    private String currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
