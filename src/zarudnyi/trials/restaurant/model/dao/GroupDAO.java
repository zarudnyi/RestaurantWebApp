package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

public interface GroupDAO {
    Group createGroup (User owner);

    Group findById (Integer id);

    List<Group> findAll();

    void removeGroup(Group group);

    void updateGroup(Group group);

    void addMember (Group group, User member);

    void removeMember (Group group, User member);

    List<User> getMembers(Group group);

    void setGroupOwner (Group group, User owner);


}
