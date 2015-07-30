package zarudnyi.trials.restaurant.model.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

@Repository("userDao")
public class UserSQLiteDAOImpl extends RestaurantAppSQLiteDao implements UserDAO{

    public User createUser() {
        Integer id = genericInsert("insert into users (fname,lname,role)values(null,null,null)");
        return findById(id);
    }

    public void updateUser(User user) {
        jdbc.update("INSERT or REPLACE into USERS (id, fname,lname,role) values (?,?,?,?)",user.getId(),user.getFname(),user.getLname(),user.getRole());

    }

    public void removeUser(User user) {
        jdbc.update("DELETE from users WHERE id=?",user.getId());
    }

    public User findById(Integer id) {
        return jdbc.queryForObject("select * from users where id=?",new Object[]{id},new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> findAll() {
        return jdbc.query("select * from users",new Object[]{},new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> findByGroup(Group group) {
        return jdbc.query("select users.* from users join user_group on users.id = user_group.user_id and user_group.group_id=?",
                new Object[]{group.getId()}, new BeanPropertyRowMapper<User>(User.class) );
    }
}
