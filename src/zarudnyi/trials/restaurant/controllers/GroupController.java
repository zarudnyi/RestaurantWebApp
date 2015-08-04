package zarudnyi.trials.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import zarudnyi.trials.restaurant.model.dao.OrderDAO;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.Order;
import zarudnyi.trials.restaurant.model.entity.User;
import zarudnyi.trials.restaurant.services.impl.GroupService;
import zarudnyi.trials.restaurant.services.impl.OrderService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GroupController {
    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private OrderService orderService;

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
                groupService.addCandidate(user, group);
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
            Boolean isOwner = false;
            if (!userService.currentUserLogin().equals("anonymousUser")) {
                User currentUser = userService.currentUser();
                isOwner = groupService.isOwner(currentUser, group);

                modelAndView.addObject("isPendingApprove", groupService.getCandidates(group).contains(currentUser));
                modelAndView.addObject("isMember", groupService.isMember(currentUser, group));
                modelAndView.addObject("isOwner", isOwner);
                List<Order> groupOrders = orderService.getUserInitiatedGroupOrders(currentUser);
                boolean groupOrderExists = false;
                for (Order order:groupOrders){
                    if (group.getId().equals(order.getGroupId()))
                        groupOrderExists = true;
                }

                modelAndView.addObject("groupOrderExists",groupOrderExists);
                modelAndView.addObject("currUser", currentUser);
            }


            modelAndView.addObject("members", groupService.getMembers(group));

            if (isOwner)
                modelAndView.addObject("candidates", groupService.getCandidates(group));
            else
                modelAndView.addObject("owner", groupService.getOwner(group));

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:/");

        }

        return modelAndView;
    }

    @RequestMapping(value = {"/groups"}, method = {RequestMethod.GET})
    public ModelAndView groupsPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("all_groups");

        Map<Group, User> owners = new HashMap<Group, User>();
        List<Group> groups = groupService.allGroups();

        for (Group group : groups) {
            owners.put(group, groupService.getOwner(group));
        }

        modelAndView.addObject("groups", groups);
        modelAndView.addObject("owners", owners);

        return modelAndView;
    }

    @RequestMapping(value = {"/groupOrder"}, method = {RequestMethod.GET})
    public String createGroupOrder(@RequestParam("group_id") Integer group_id) {

        try {
            User currentUser = userService.currentUser();
            Group group = groupService.findById(group_id);
            groupService.isOwner(currentUser,group);
            orderService.placeGroupOrder(currentUser, group);


        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/group?id="+group_id;
    }

}
