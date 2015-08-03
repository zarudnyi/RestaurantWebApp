package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

public interface GroupDAO {
    Integer MEMBER_OPTION = 0;
    Integer OWNER_OPTION = 1;
    Integer CANDIDATE_OPTION = 2;
    Integer CANDIDATE_REJECTED_OPTION = 3;


    Group createGroup (User owner);

    Group findById (Integer id);

    List<Group> findAll();

    void removeGroup(Group group);

    void updateGroup(Group group);

    void addMember (Group group, User member);

    void addUser(Group group, User user, Integer option);

    void removeUser(Group group, User member);

    List<User> getMembers(Group group);

    List<User> getCandidates (Group group);


    List<Group> findByUser(User user);

    void setGroupOwner (Group group, User owner);

    Integer getOwnerId(Group group);


}
