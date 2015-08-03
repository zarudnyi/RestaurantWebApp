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


        if (groupService.isOwner(userService.currentUser(), group)) {
            groupService.removeGroup(group);
        }

        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = {"/leaveGroup"}, method = {RequestMethod.GET})
    public ModelAndView leaveGroup(@RequestParam("group_id") Integer group_id) {

        try {
            Group group = groupService.findById(group_id);

            if (groupService.isMember(userService.currentUser(), group)) {
                groupService.removeUserFromGroup(userService.currentUser(), group);
            }
        } catch (Exception e) {
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
            if (groupService.isOwner(userService.currentUser(), group)) {
                groupService.removeUserFromGroup(user, group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/group?id=" + group_id);
    }

    @RequestMapping(value = {"/rejectUserFromGroup"}, method = {RequestMethod.GET})
    public ModelAndView rejectUserFromGroup(@RequestParam("group_id") Integer group_id,
                                            @RequestParam("user_id") Integer user_id) {

        try {
            Group group = groupService.findById(group_id);
            User user = userService.getUserById(user_id);
            User currentUser = userService.currentUser();
            if (groupService.isOwner(currentUser, group) && groupService.getCandidates(group).contains(user)) {
                groupService.rejectCandidate(user, group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/group?id=" + group_id);
    }


    @RequestMapping(value = {"/approveUserToGroup"}, method = {RequestMethod.GET})
    public ModelAndView approveUserToGroup(@RequestParam("group_id") Integer group_id,
                                           @RequestParam("user_id") Integer user_id) {

        try {
            Group group = groupService.findById(group_id);
            User user = userService.getUserById(user_id);
            User currentUser = userService.currentUser();
            if (groupService.isOwner(currentUser, group) && groupService.getCandidates(group).contains(user)) {
                groupService.approveCandidate(user, group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/group?id=" + group_id);
    }

    @RequestMapping(value = {"/inviteToGroup"}, method = {RequestMethod.GET})
    public ModelAndView inviteToGroup(@RequestParam("group_id") Integer group_id,
                                      @RequestParam("user_id") Integer user_id) {

        try {
            Group group = groupService.findById(group_id);
            User user = userService.getUserById(user_id);
            User currentUser = userService.currentUser();
            if (!groupService.isMember(currentUser, group) && !groupService.getCandidates(group).contains(user)) {
                groupService.ascInviteToGroup(user, group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/group?id=" + group_id);
    }

    @RequestMapping(value = {"/group"}, method = {RequestMethod.GET})
    public ModelAndView groupPage(@RequestParam("id") Integer group_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("group");

        try {
            Group group = groupService.findById(group_id);
            modelAndView.addObject("group", group);
            Boolean isOwner;
            if (userService.currentUserLogin()!=null)
                isOwner = groupService.isOwner(userService.currentUser(), group);
            else

            modelAndView.addObject("isOwner", isOwner.toString());
            modelAndView.addObject("members", groupService.getMembers(group));

            if (isOwner)
                modelAndView.addObject("candidates", groupService.getCandidates(group));


        } catch (Exception e) {
            modelAndView.setViewName("index");

        }

        return modelAndView;
    }
}
