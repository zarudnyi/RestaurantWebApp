package zarudnyi.trials.restaurant.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zarudnyi.trials.restaurant.model.dao.GroupDAO;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupDAO groupDAO;

    @Autowired
    UserDAO userDAO;

    public Group findById (Integer id){
        return groupDAO.findById(id);
    }

    public List<Group> getUserGroups (User user){
        return groupDAO.findByUser(user);
    }

    public boolean isOwner (User user, Group group){
        return groupDAO.getOwnerId(group).equals(user.getId());
    }

    public void removeGroup(Group g){
        groupDAO.removeGroup(g);
    }

    public boolean isMember(User user, Group group) {
        List<User> members = groupDAO.getMembers(group);
        return members.contains(user);
    }

    public void removeUserFromGroup(User user, Group group) {
        groupDAO.removeMember(group,user);
    }

    public List<User> getMembers(Group group) {
        return groupDAO.getMembers(group);
    }
}