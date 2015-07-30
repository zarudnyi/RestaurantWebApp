package zarudnyi.trials.restaurant.model.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.GroupDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.dao.UserDAO;
import zarudnyi.trials.restaurant.model.entity.Group;
import zarudnyi.trials.restaurant.model.entity.User;

import java.util.List;

@Repository("groupDao")
public class GroupSQLiteDAOImpl extends RestaurantAppSQLiteDao implements GroupDAO {
    private static final Integer OWNER_OPTION = 1;
    @Autowired
    private UserDAO userDAO;

    public Group createGroup(User owner) {
        Integer id = genericInsert("insert into groups (name,description) values(null,null)");
        return findById(id);
    }

    public Group findById(Integer id) {
        return jdbc.queryForObject("select * from groups where id=?",new Object[]{id},new BeanPropertyRowMapper<Group>(Group.class));
    }

    public List<Group> findAll() {
        return jdbc.query("select * from groups", new Object[]{}, new BeanPropertyRowMapper<Group>(Group.class));
    }

    public void removeGroup(Group group) {
        jdbc.update("DELETE FROM groups WHERE id=?",group.getId());
    }

    public void updateGroup(Group group) {
        jdbc.update("INSERT or replace into groups (id, name, description) VALUES (?,?,?)",group.getId(),group.getName(),group.getDescription());

    }

    public void addMember(Group group, User member) {
        if (jdbc.queryForObject("select count (1) from user_group where user_id=? and group_id=?",new Object[]{member.getId(),group.getId()},Integer.class ) .compareTo(0)==0)
            jdbc.update("INSERT INTO user_group (user_id, group_id, option) VALUES (?,?,NULL)",member.getId(),group.getId());
    }

    public void removeMember(Group group, User member) {
        jdbc.update("DELETE from user_group WHERE group_id=? and user_id=?",group.getId(),member.getId());
    }

    public List<User> getMembers(Group group) {
        return userDAO.findByGroup(group);
    }

    public void setGroupOwner(Group group, User owner) {
        jdbc.update("delete from user_group where user_id=? and group_id=?", owner.getId(), group.getId());
        jdbc.update("INSERT INTO user_group (user_id, group_id, option) VALUES (?,?,?)",owner.getId(),group.getId(),OWNER_OPTION);
    }


}
