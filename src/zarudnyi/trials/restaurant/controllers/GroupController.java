package zarudnyi.trials.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;
import zarudnyi.trials.restaurant.services.impl.GroupService;
import zarudnyi.trials.restaurant.services.impl.UserService;

@Controller
public class GroupController {
    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;



    @RequestMapping(value = {"/removeGroup"}, method = {RequestMethod.GET})
    public ModelAndView removeGroup(@RequestParam("group_id") Integer group_id) {
        Group group = groupService.findById(group_id);


        if (groupService.isOwner(userService.currentUser(), group)){
            groupService.removeGroup(group);
        }

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = {"/leaveGroup"}, method = {RequestMethod.GET})
    public ModelAndView leaveGroup(@RequestParam("group_id") Integer group_id) {

        try {
            Group group = groupService.findById(group_id);

            if (groupService.isMember(userService.currentUser(), group)){
                groupService.removeUserFromGroup(userService.currentUser(), group);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = {"/removeUserFromGroup"}, method = {RequestMethod.GET})
    public ModelAndView removeUserFromGroup(@RequestParam("group_id") Integer group_id,
                                            @RequestParam("user_id") Integer user_id) {

        try {
            Group group = groupService.findById(group_id);
            User user = userService.getUserById(user_id);
            if (groupService.isOwner(userService.currentUser(), group) ){
                groupService.removeUserFromGroup(user, group);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/group?id="+group_id);
    }

    @RequestMapping(value = {"/group"}, method = {RequestMethod.GET})
    public ModelAndView groupPage(@RequestParam("id") Integer group_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("group");

        try {
            Group group = groupService.findById(group_id);
            modelAndView.addObject("group",group);
            modelAndView.addObject("isOwner",groupService.isOwner(userService.currentUser(),group)+"");
            modelAndView.addObject("members", groupService.getMembers(group));

        }catch (Exception e){
            modelAndView.setViewName("index");

        }

        return modelAndView;
    }
}
