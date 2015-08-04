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

    @Autowired
    private UserDAO userDAO;

    public Group createGroup(User owner) {
        Integer id = genericInsert("insert into groups (name,description) values(null,null)");
        return findById(id);
    }

    public Group findById(Integer id) {
        return jdbc.queryForObject("SELECT * FROM groups WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<Group>(Group.class));
    }

    public List<Group> findAll() {
        return jdbc.query("SELECT * FROM groups", new Object[]{}, new BeanPropertyRowMapper<Group>(Group.class));
    }

    public void removeGroup(Group group) {
        jdbc.update("DELETE FROM user_group WHERE group_id=?", group.getId());
        jdbc.update("DELETE FROM groups WHERE id=?", group.getId());
    }

    public void updateGroup(Group group) {
        jdbc.update("INSERT OR REPLACE INTO groups (id, name, description) VALUES (?,?,?)", group.getId(), group.getName(), group.getDescription());

    }

    public void addMember(Group group, User member) {
        jdbc.update("INSERT INTO user_group (user_id, group_id, option) VALUES (?,?,?)", member.getId(), group.getId(),MEMBER_OPTION);
    }

    public void addUser(Group group, User member, Integer option) {
        jdbc.update("INSERT INTO user_group (user_id, group_id, option) VALUES (?,?,?)", member.getId(), group.getId(),option);
    }

    public void removeUser(Group group, User member) {
        if (getOwnerId(group).equals(member.getId()))
            removeGroup(group);
        else
            jdbc.update("DELETE FROM user_group WHERE group_id=? AND user_id=?", group.getId(), member.getId());
    }

    public List<User> getMembers(Group group) {
        return userDAO.findByGroup(group);
    }

    public List<User> getCandidates(Group group) {
        return userDAO.findByGroup(group, CANDIDATE_OPTION);
    }

    public List<Group> findByUser(User user) {
        return jdbc.query("SELECT groups.* FROM groups JOIN user_group ON groups.id = user_group.group_id WHERE user_group.user_id=? and user_group.option in (?,?)", new Object[]{user.getId(), MEMBER_OPTION,OWNER_OPTION}, new BeanPropertyRowMapper<Group>(Group.class));
    }

    public void setGroupOwner(Group group, User owner) {
        jdbc.update("DELETE FROM user_group WHERE user_id=? AND group_id=?", owner.getId(), group.getId());
        jdbc.update("INSERT INTO user_group (user_id, group_id, option) VALUES (?,?,?)", owner.getId(), group.getId(), OWNER_OPTION);
    }

    public Integer getOwnerId(Group group) {
        return jdbc.queryForObject("SELECT groups.id FROM groups JOIN user_group ON groups.id = user_group.group_id WHERE groups.id=? AND user_group.option=? ", new Object[]{group.getId(), OWNER_OPTION}, Integer.class);
    }


}
