package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

public interface UserDAO {

    User createUser(String login);

    void updateUser (User user);

    void removeUser (User user);

    User findById (Integer id);

    User findByLogin (String login);

    List<User> findAll();

    List<User> findByGroup(Group group);

    List<User> findByGroup(Group group, Integer option);


}
