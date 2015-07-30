package zarudnyi.trials.restaurant.model.dao.impl.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.model.entity.MenuItem;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zarudnyi on 30.07.2015.
 */
public class MenuItemRowMapper implements RowMapper<MenuItem> {
    public MenuItem mapRow(ResultSet resultSet, int i) throws SQLException {

        MenuItem menuItem = new MenuItem();
        menuItem.setId(resultSet.getInt(1));
        menuItem.setName(resultSet.getString(3));
        menuItem.setPrice(resultSet.getInt(4));
        menuItem.setDescription(resultSet.getString(5));

        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setId(resultSet.getInt(6));
        menuCategory.setName(resultSet.getString(7));
        menuCategory.setDescription(resultSet.getString(8));

        menuItem.setCategory(menuCategory);

        return menuItem;

    }
}
