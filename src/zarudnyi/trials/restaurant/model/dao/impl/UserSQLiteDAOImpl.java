package zarudnyi.trials.restaurant.model.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.GroupDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

@Repository("userDao")
public class UserSQLiteDAOImpl extends RestaurantAppSQLiteDao implements UserDAO{

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String login) {
        Integer id = genericInsert("insert into users (login,password,fname,lname,role)values(?,null,null,null,null)",login);
        return findById(id);
    }

    public void updateUser(User user) {
        String password;
        if (!user.getPassword().contains("{bcrypt}"))
            password = "{bcrypt}"+passwordEncoder.encode( user.getPassword());
        else
            password = user.getPassword();

        jdbc.update("INSERT or REPLACE into USERS (id,login,password, fname,lname,role) values (?,?,?,?,?,?)",user.getId(),user.getLogin(),password,user.getFname(),user.getLname(),user.getRole());
    }

    public void removeUser(User user) {
        jdbc.update("DELETE from users WHERE id=?",user.getId());
    }

    public User findById(Integer id) {
        return jdbc.queryForObject("select * from users where id=?",new Object[]{id},new BeanPropertyRowMapper<User>(User.class));
    }

    public User findByLogin(String login) {
        return jdbc.queryForObject("select * from users where login=?",new Object[]{login},new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> findAll() {
        return jdbc.query("select * from users",new Object[]{},new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> findByGroup(Group group) {
        return jdbc.query("select users.* from users join user_group on users.id = user_group.user_id and user_group.group_id=? and user_group.option in (?,?)",
                new Object[]{group.getId(), GroupDAO.MEMBER_OPTION, GroupDAO.OWNER_OPTION}, new BeanPropertyRowMapper<User>(User.class) );
    }

    public List<User> findByGroup(Group group, Integer option) {
        return jdbc.query("select users.* from users join user_group on users.id = user_group.user_id and user_group.group_id=? and user_group.option=?",
                new Object[]{group.getId(), option}, new BeanPropertyRowMapper<User>(User.class) );    }
}
