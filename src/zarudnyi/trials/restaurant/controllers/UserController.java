package zarudnyi.trials.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;
import zarudnyi.trials.restaurant.model.validator.UserValidator;
import zarudnyi.trials.restaurant.services.impl.GroupService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;


    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public ModelAndView loginPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = {"/profile"}, method = {RequestMethod.GET})
    ModelAndView profilePage() {
        ModelAndView model = new ModelAndView();

        model.setViewName("profile");
        User curUser = userService.getUserByLogin(userService.currentUserLogin());
        model.addObject("user", curUser);

        List<Group> userGroups = groupService.getUserGroups(curUser);
        List<Group> myGroups = new ArrayList<Group>();
        List<Group> memberOfGroups = new ArrayList<Group>();

        for (Group g : userGroups){
            if (groupService.isOwner(curUser,g))
                myGroups.add(g);
            else
                memberOfGroups.add(g);
        }
        model.addObject("myGroups", myGroups);
        model.addObject("memberOfGroups", memberOfGroups);


        return model;
    }

    @RequestMapping(value = {"/updateUser"}, method = {RequestMethod.POST})
    public ModelAndView updateUser(@ModelAttribute User user, BindingResult result) {

        user.setLogin(userService.currentUserLogin());
        if (user.getPassword().isEmpty())
            user.setPassword(userService.currentUser().getPassword());

        userValidator.validate(user, result);

        if (result.hasErrors()){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("profile");
            modelAndView.addObject("user",user);
            return modelAndView;
        }else {
            userService.updateUser(user);
            return new ModelAndView("redirect:/profile");
        }
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET})
    ModelAndView registerPage() {
        ModelAndView model = new ModelAndView();

        model.setViewName("register");
        model.addObject("user", new User());
        return model;
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public ModelAndView registerUser(@ModelAttribute User user, BindingResult result) {

        userValidator.validate(user, result);

        if (result.hasErrors()){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            modelAndView.addObject("user",user);
            return modelAndView;
        }else {
            userService.createUser(user);
            return new ModelAndView("redirect:/profile");
        }

    }


}
